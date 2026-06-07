#!/usr/bin/env bash
set -euo pipefail

TOPIC="face-recognition-events"
KAFKA_CONTAINER="kafka"

if [ $# -lt 1 ]; then
  echo "Usage: $0 '<raw json>'"
  echo "Example: $0 '{\"studentName\":\"Ivan\",\"studentLastName\":\"Ivanov\",\"classroomName\":\"A-101\",\"recognizedAt\":\"2026-06-07T19:00:00\",\"confidence\":0.95}'"
  exit 1
fi

echo "Sending to $TOPIC: $1"

echo "$1" | docker exec -i "$KAFKA_CONTAINER" /opt/kafka/bin/kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic "$TOPIC"

echo "Sent."
