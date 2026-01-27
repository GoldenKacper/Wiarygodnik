import { cleanupOutdatedCaches } from 'workbox-precaching';
import { registerRoute } from 'workbox-routing';
import { NetworkFirst, NetworkOnly } from 'workbox-strategies';
import { BackgroundSyncPlugin } from 'workbox-background-sync';
import { ExpirationPlugin } from 'workbox-expiration';
import { CacheableResponsePlugin } from 'workbox-cacheable-response';
import { precacheAndRoute } from 'workbox-precaching';
import { clientsClaim } from 'workbox-core'
import { incrementUnreadNotifications, decrementUnreadNotifications } from './features/push/lib/badging-db';

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
  ({ url }) => url.pathname === '/api/analysis/process',
  new NetworkOnly({
    plugins: [
      new BackgroundSyncPlugin('analysisProcessBackgroundSyncQueue', {
        maxRetentionTime: 24 * 60
      })
    ]
  }),
  'POST'
);

registerRoute(
  ({ url }) => /^\/api\/report\/.+$/.test(url.pathname),
  new NetworkOnly({
    plugins: [
      new BackgroundSyncPlugin('deleteReportBackgroundSyncQueue', {
        maxRetentionTime: 24 * 60
      })
    ]
  }),
  'DELETE'
);


self.addEventListener('push', (event: PushEvent) => {
  if (!event.data) return;

  const data = event.data.json();

  event.waitUntil(
    (async () => {
      await incrementUnreadNotifications();

      self.registration.showNotification(data.title, {
        body: data.body,
        icon: '/web-app-manifest-192x192.png',
        badge: '/web-app-manifest-192x192.png',
        data: {
          url: data.url
        }
      })

      const allClients = await self.clients.matchAll();
      allClients.forEach(client => client.postMessage({ type: 'SYNC_BADGE' }));
    })()
  );
})

self.addEventListener('notificationclick', (event: NotificationEvent) => {
  event.notification.close();

  event.waitUntil(
    (async () => {
      await decrementUnreadNotifications();

      self.clients.openWindow(event.notification.data.url)
        .then(client => client?.postMessage({ type: 'SYNC_BADGE' }))
    })()
  );
})
