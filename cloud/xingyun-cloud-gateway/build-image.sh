#!/bin/sh

set -eu

usage() {
  echo "Usage: sh build-image.sh [image:tag]"
  echo
  echo "Builds the xingyun-cloud-gateway jar and Docker image."
  echo "Default image tag: xingyun-cloud-gateway:local"
}

if [ "${1:-}" = "-h" ] || [ "${1:-}" = "--help" ]; then
  usage
  exit 0
fi

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
REPO_ROOT=$(CDPATH= cd -- "$SCRIPT_DIR/../.." && pwd)
IMAGE_TAG=${1:-xingyun-cloud-gateway:local}

echo "Packaging xingyun-cloud-gateway..."
mvn -f "$REPO_ROOT/pom.xml" -pl cloud/xingyun-cloud-gateway -am package -DskipTests

echo "Building Docker image: $IMAGE_TAG"
docker build -t "$IMAGE_TAG" -f "$SCRIPT_DIR/target/Dockerfile" "$SCRIPT_DIR/target"

echo "Done: $IMAGE_TAG"
