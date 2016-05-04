#!/bin/sh
#脚本使用说明
Usage(){
	echo "Git 使用脚本
	Usage ：
	#add commit push 
	./update.sh push commit 内容
	./update.sh push  提交内容"
}
TODO=$1
DESC=$2

push(){
	git add .
	git st
	git ci "$DESC"
	git push os master
	git push gb master
}
die(){
	echo #print 
	echo "$*"
	Usage
	echo
	exit 1
}
case $TODO in
	push )
		push
		;;
	*)
die	"参数错误"
;;
esac
