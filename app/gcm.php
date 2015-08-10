<?php

function sendPushNotificationToGCM($registatoin_ids, $message) {
    //Google cloud messaging GCM-API url
    $SERVER_KEY = "AIzaSyD4uQoX5hVsINfABKbmuywxYT77TTtdrjE";
    $url = 'https://android.googleapis.com/gcm/send';
    $fields = array(
        'registration_ids' => $registatoin_ids,
        'time_to_live' => 82800, // 23 hour
        'data' => $message,
    );
    // Google Cloud Messaging GCM API Key 
    // Public API access -> Key for server applications -> Edit allowed IPs(public ip)
    $headers = array(
        'Authorization: key=' . $SERVER_KEY,
        'Content-Type: application/json'
    );
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, TRUE);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
    $result = curl_exec($ch);
    if ($result === FALSE) {
        die('Curl failed: ' . curl_error($ch));
    }
    curl_close($ch);
    return $result;
}

$text = $_POST['text'];

// must be array
$to = array();
$to[] = $_POST['reg_id'];

echo sendPushNotificationToGCM( $to, array('text' => $text));
