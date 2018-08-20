#!/usr/bin/env bash

function cd_git_pull() { # git pull
    for cf in `pwd`/*; do
        if [ -d $cf ]; then
            echo $cf
            cd $cf
            if [ -d ".git" ]; then
                git pull
                echo ''
            else
                cd_git_pull
            fi
            cd ..
        fi
    done
}

cd_git_pull

