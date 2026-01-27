import { Button, Tooltip, Typography } from '@mui/material'
import NotificationsIcon from '@mui/icons-material/Notifications'
import NotificationsOffIcon from '@mui/icons-material/NotificationsOff';
import { usePushNotifications } from '../hooks/usePushNotifications'
import { useOnlineStatus } from '../../../hooks/useOnlineStatus'
import { useState, useEffect } from 'react';
import { userPageButton, userPageButtonIcon, userPageButtonText } from '../../../Style';

export const PushNotificationButton = () => {
  const online = useOnlineStatus();

  const [subscribed, setSubscribed] = useState(false)
  const { subscribe, unsubscribe, isSubscribed } = usePushNotifications()

  const handleClick = async () => {
    try {
      if (subscribed) {
        const success = await unsubscribe();
        if (success) setSubscribed(false);
      } else {
        const sub = await subscribe();
        if (sub) setSubscribed(true);
      }
    } catch (err) {
      console.error(err);
    }
  }

  useEffect(() => {
    isSubscribed().then(setSubscribed)
  }, [])

  return (
    <Tooltip title={online ? "" : "Niedostępne w trybie offline"}>
      <Button aria-label={"notifications"} sx={userPageButton} onClick={handleClick}>
        {subscribed ? <NotificationsOffIcon sx={userPageButtonIcon} /> : <NotificationsIcon sx={userPageButtonIcon} />}
        <Typography sx={userPageButtonText}>{subscribed ? "Wyłącz powiadomienia" : "Włącz powiadomienia"}</Typography>
      </Button>
    </Tooltip>
  )
}