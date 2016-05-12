<?php
include_once('Easemob.class.php');

$options['client_id']="YXA6beRs0BBVEeaT4BFD7m4AXQ";
$options['client_secret']="YXA6I206cjkFiBnVZreH-i5hflbUYVA";
$options['org_name']="dianshiju";
$options['app_name']="mdcmoe";
$easemob=new Easemob($options);

if(isset($_POST['username']) && isset($_POST['password'])){
	$account['username']=$_POST['username'] ;  	
	$account['password']=$_POST['password'];
	//这里处理自己服务器注册的流程
	//自己服务器注册成功后向环信服务器注册
	$result=$easemob->accreditRegister($account);
	echo $result;
}else{
	$res['status']=404;
	$res['message']="params is not right";
	echo json_encode($res);
}

?>