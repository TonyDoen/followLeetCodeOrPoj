app=$1
if [ -z $1 ];then
    echo -n "please input the appName:"
    read app
fi

echo $app

env=$2
if [ -z $2 ];then
        echo -n "please input the env:"
        read env
fi

echo $env

version=$3
if [ -z $3 ];then
    echo -n "please input the version:"
    read version
fi

echo $version

test ! -d /home/proj/archive/ && mkdir -p /home/proj/archive/

test -d /home/proj/$app && rm -rf /home/proj/$app
mkdir -p /home/proj/$app
unzip  /home/proj/${env}-${app}-1*.zip -d /home/proj/$app
echo "unzip  success"

mv /home/proj/${env}-${app}-*.zip /home/proj/archive/
echo "archive success"

kill -9 `ps -ef | grep "/home/proj/$app/conf" | awk '{print $2}'`
echo "stop success"
sleep 3

rm -rf /data/cache/*

sh /home/proj/$app/bin/start.sh debug
echo "start /home/proj/$app/bin/startup.sh , please check"