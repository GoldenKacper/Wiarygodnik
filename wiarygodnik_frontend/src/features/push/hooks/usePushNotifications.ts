import { useCallback } from 'react';
import { usePushApi } from '../api/push.api';

const VAPID_PUBLIC_KEY = import.meta.env.VITE_VAPID_PUBLIC_KEY;

function urlBase64ToUint8Array(base64String: string) {
  const padding = '='.repeat((4 - (base64String.length % 4)) % 4);
  const base64 = (base64String + padding).replace(/-/g, '+').replace(/_/g, '/');
  const rawData = atob(base64);
  return new Uint8Array(rawData.length).map((_, i) => rawData.charCodeAt(i));
};

export const usePushNotifications = () => {
  const { postSubscribe, postUnsubscribe } = usePushApi();

  const askPermission = useCallback(async () => {
    if (!('Notification' in window)) {
      throw new Error('Notifications not supported');
    }

    const permission = await Notification.requestPermission();
    if (permission !== 'granted') {
      throw new Error('Permission denied');
    }

    return permission;
  }, []);

  const subscribe = useCallback(async (): Promise<PushSubscription | null> => {
    if (!('serviceWorker' in navigator)) return null;
    if (!('PushManager' in window)) return null;

    const registration = await navigator.serviceWorker.ready;

    let subscription = await registration.pushManager.getSubscription();
    if (subscription) return subscription;

    await askPermission();

    subscription = await registration.pushManager.subscribe({
      userVisibleOnly: true,
      applicationServerKey: urlBase64ToUint8Array(VAPID_PUBLIC_KEY)
    });

    await postSubscribe(subscription);

    return subscription;
  }, [askPermission]);

  const unsubscribe = async (): Promise<boolean | null> => {
    if (!('serviceWorker' in navigator)) return null;
    if (!('PushManager' in window)) return null;

    const registration = await navigator.serviceWorker.ready;

    const subscription = await registration.pushManager.getSubscription();
    if (!subscription) return false;

    const success = await subscription.unsubscribe();

    await postUnsubscribe(subscription);

    return success;
  }

  const isSubscribed = useCallback(async (): Promise<boolean> => {
    if (!('serviceWorker' in navigator)) return false
    if (!('PushManager' in window)) return false

    const registration = await navigator.serviceWorker.ready
    const subscription = await registration.pushManager.getSubscription()

    return subscription !== null
  }, [subscribe]);

  return { askPermission, subscribe, unsubscribe, isSubscribed };
}
