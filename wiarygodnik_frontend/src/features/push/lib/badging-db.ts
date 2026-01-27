const DB_NAME = 'push-db';
const STORE = 'meta';

function openDB(): Promise<IDBDatabase> {
  return new Promise((resolve, reject) => {
    const req = indexedDB.open(DB_NAME, 1);
    req.onupgradeneeded = () => {
      req.result.createObjectStore(STORE);
    };
    req.onsuccess = () => resolve(req.result);
    req.onerror = () => reject(req.error);
  });
}

async function getUnread(): Promise<number> {
  const db = await openDB();
  return new Promise((resolve) => {
    const tx = db.transaction(STORE, 'readonly');
    const req = tx.objectStore(STORE).get('unread');
    req.onsuccess = () => resolve(req.result ?? 0);
  });
}

async function setUnread(value: number) {
  const db = await openDB();
  const tx = db.transaction(STORE, 'readwrite');
  tx.objectStore(STORE).put(value, 'unread');
}

export async function incrementUnreadNotifications() {
  const unread = await getUnread();
  await setUnread(unread + 1);
}

export async function decrementUnreadNotifications() {
  const unread = await getUnread();
  await setUnread(Math.max(unread - 1, 0));
}

export async function clearUnreadNotifications() {
  await setUnread(0);
}

export async function syncBadge() {
  const unread = await getUnread();
  if ('setAppBadge' in navigator) {
    navigator.setAppBadge(unread);
  }
}
