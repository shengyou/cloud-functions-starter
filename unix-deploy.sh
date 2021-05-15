gcloud functions deploy text \
   --region asia-east2 \
   --runtime java11 \
   --trigger-http \
   --memory 256MB \
   --source=build/deploy \
   --entry-point io.kraftsman.handlers.PlainTextHandler \
   --allow-unauthenticated
