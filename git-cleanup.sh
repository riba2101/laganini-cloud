#!/usr/bin/env bash

function traverse() {
  echo "Cleaning $1 in $(pwd)"
  cd "./$1"
  clean "$1"
  cd ..
}

function clean() {
    rm -rf .git
  #  git init
  #  git add --all
  #  git remote remove origin
  #  git remote add origin "git@bitbucket.org:laganini/$1.git"
  #
  #  git pull origin master --allow-unrelated-histories
  #
  #  git checkout --orphan temp_branch
  #
  #  git add -A
  #  git commit -am "initial commit"
  #
  #  git fetch
  #
  #  git branch -D master
  #
  #  git branch -m master
  #

#  git push --set-upstream origin master
#  git fetch --all --tags

  #Delete local tags.
#  git tag -l | xargs git tag -d
  #Fetch remote tags.
#  git fetchgit
  #Delete remote tags.
#  git tag -l | xargs -n 1 git push --delete origin
  #Delete local tasg.
#  git tag -l | xargs git tag -d

#  git push -f origin master
}

services=(
  laganini-cloud-dependencies
  laganini-cloud-exception
  laganini-cloud-common
  laganini-cloud-logging
  laganini-cloud-metrics-parent
  laganini-cloud-parent
  laganini-cloud-rmi-parent
  laganini-cloud-storage-audit-parent
  laganini-cloud-storage-parent
  laganini-cloud-test-suite
  laganini-cloud-validation
)
for i in "${services[@]}"; do
  traverse $i
done
