<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<style type="text/css">
#MsgContainer{
    background:#ffffe5;
    position:relative;
    overflow:hidden;
    width:550px;
    height:20px;
    line-height:20px;
    margin:5px;
}

#MsgContent{
    position:absolute;
    left:0;
    top:3px;
    color: #66C;
    font-family:"微软雅黑";
    white-space:nowrap;
}
</style>
<div id="MsgContainer">
    <div id="MsgContent">运营管理系统正在试运行，具有问题件工单、回访不成功件、续期催缴、新契约不合格件、发票申请等的管理。登录可以使用核心业务系统工号，默认密码可联系省分运营管理部。 请在试用过程中积极反馈意见和建议，谢谢。 </div>
</div>
<script type="text/javascript">
if(!window.rollWord){
     var rollWord = {
           MsgContainer:document.getElementById("MsgContainer"),
           MsgContent:document.getElementById("MsgContent"),
           _containerWidth:1,
           _contentWidth:1,
           _speed:1,
           setSpeed:function(opt){
                 var This = this;
                 This._speed = opt;
           },
           setContainerWidth:function(){
                 var This = this;
                 This._containerWidth = This.MsgContainer.offsetWidth;
           },
           setContentWidth:function(){
                 var This = this;
                 This._contentWidth = This.MsgContent.offsetWidth;
           },
           roll:function(){
                 var This = this;
                 This.MsgContent.style.left = parseInt(This._containerWidth) + "px";
                 var time = setInterval(function(){This.move()},20);
                 This.MsgContainer.onmouseover = function(){
                      clearInterval(time);
                 };
                 This.MsgContainer.onmouseout = function(){
                      time = setInterval(function(){This.move()},20);
                 };
           },
           move:function(){
                 var This = this;
                 if(parseInt(This.MsgContent.style.left)+This._contentWidth > 0)
                 {
                      This.MsgContent.style.left = parseInt(This.MsgContent.style.left)-This._speed + "px";
                 }
                  else
                  {
                      This.MsgContent.style.left = parseInt(This._containerWidth) + "px";
                  }                 
           },
           init:function(opt){
                var This = this;
                var speed = opt.speed || 1;
                This.setSpeed(speed);
                This.setContainerWidth();
                This.setContentWidth();
                This.roll();
           }
      }
}
      rollWord.init({speed:1});
</script>