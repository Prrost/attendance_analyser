#!/usr/bin/env bash
set -euo pipefail

TOPIC="face-recognition-events"
KAFKA_CONTAINER="kafka"

if [ $# -lt 3 ]; then
  echo "Usage: $0 <studentName> <studentLastName> <classroomName> [confidence] [recognizedAt]"
  echo "Example: $0 Ivan Ivanov A-101"
  echo "Example: $0 Ivan Ivanov A-101 0.87 2026-06-07T19:00:00"
  exit 1
fi

STUDENT_NAME="$1"
STUDENT_LAST_NAME="$2"
CLASSROOM_NAME="$3"
CONFIDENCE="${4:-0.95}"
RECOGNIZED_AT="${5:-$(TZ=Asia/Almaty date +%Y-%m-%dT%H:%M:%S)}"

JSON=$(printf '{"studentName":"%s","studentLastName":"%s","classroomName":"%s","recognizedAt":"%s","confidence":%s}' \
"$STUDENT_NAME" "$STUDENT_LAST_NAME" "$CLASSROOM_NAME" "$RECOGNIZED_AT" "$CONFIDENCE")

echo "Sending to $TOPIC: $JSON"

echo "$JSON" | docker exec -i "$KAFKA_CONTAINER" /opt/kafka/bin/kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic "$TOPIC"

echo "Sent."
