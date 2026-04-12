#!/bin/sh

set -eu

usage() {
  echo "Usage: sh build-image.sh [image:tag]"
  echo
  echo "Builds the xingyun-api jar and Docker image."
  echo "Default image tag: xingyun-api:local"
}

if [ "${1:-}" = "-h" ] || [ "${1:-}" = "--help" ]; then
  usage
  exit 0
fi

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
REPO_ROOT=$(CDPATH= cd -- "$SCRIPT_DIR/.." && pwd)
IMAGE_TAG=${1:-xingyun-api:local}

echo "Packaging xingyun-api..."
mvn -f "$REPO_ROOT/pom.xml" -pl xingyun-api -am package -DskipTests

echo "Building Docker image: $IMAGE_TAG"
docker build -t "$IMAGE_TAG" -f "$SCRIPT_DIR/target/Dockerfile" "$SCRIPT_DIR/target"

echo "Done: $IMAGE_TAG"
