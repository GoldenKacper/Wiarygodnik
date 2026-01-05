import { cleanupOutdatedCaches } from 'workbox-precaching';
import { registerRoute } from 'workbox-routing';
import { NetworkFirst, NetworkOnly } from 'workbox-strategies';
import { BackgroundSyncPlugin } from 'workbox-background-sync';
import { ExpirationPlugin } from 'workbox-expiration';
import { CacheableResponsePlugin } from 'workbox-cacheable-response';
import { precacheAndRoute } from 'workbox-precaching';
import { clientsClaim } from 'workbox-core'

declare let self: ServiceWorkerGlobalScope;

precacheAndRoute(self.__WB_MANIFEST);

cleanupOutdatedCaches();

self.skipWaiting()
clientsClaim()

registerRoute(
  ({ url }) => url.pathname.startsWith('/api'),
  new NetworkFirst({
    cacheName: 'api-cache',
    networkTimeoutSeconds: 3,
    plugins: [
      new ExpirationPlugin({
        maxEntries: 50,
        maxAgeSeconds: 60 * 5
      }),
      new CacheableResponsePlugin({
        statuses: [0, 200]
      })
    ]
  }),
  'GET'
);

registerRoute(
  ({ url }) => url.pathname.startsWith('/api'),
  new NetworkOnly({
    plugins: [
      new BackgroundSyncPlugin('backgroundSyncQueue', {
        maxRetentionTime: 24 * 60
      })
    ]
  }),
  'POST'
);


self.addEventListener('push', (event: PushEvent) => {
  if (!event.data) return;

  const data = event.data.json();

  event.waitUntil(
    self.registration.showNotification(data.title, {
      body: data.body,
      icon: '/web-app-manifest-192x192.png',
      badge: '/web-app-manifest-192x192.png',
      data: {
        url: data.url
      }
    })
  );
})

self.addEventListener('notificationclick', (event: NotificationEvent) => {
  event.notification.close();

  event.waitUntil(
    self.clients.openWindow(event.notification.data.url)
  );
})
