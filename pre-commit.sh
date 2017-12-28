#!/usr/bin/env bash

set -e
log() {
	echo "[$0] $@"
}
handle_failure() {
	log "FAILURE"
	exit 1
}
trap "handle_failure" ERR INT TERM

./gradlew clean test

tput setaf 3
printf "\nOnce you publish a new version of ida-utils "
tput bold
printf "PLEASE DON'T FORGET "
tput sgr0
tput setaf 3
printf "to update the following dependent projects:\n"

printf "\n verify-hub"
printf "\n ida-compliance-tool"
printf "\n ida-sample-rp"
printf "\n ida-stub-idp"
printf "\n ida-hub-support"
printf "\n verify-matching-service-adapter"
printf "\n doc-checking\n"

tput bold
printf "\nThank you! :)\n\n"
tput sgr0
