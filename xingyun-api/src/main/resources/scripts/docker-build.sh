#!/bin/sh
now=`date '+%Y%m%d%H%M%S'`
tag='@build.finalName@:1.0.0-'${now}
docker build -t ${tag} ../