<%@ page language="java" contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<style type="text/css">
#Warnning{
    background:#ffffe5;
    position:relative;
    overflow:hidden;
    width:550px;
    height:20px;
    line-height:20px;
    margin:5px;
}

#WarningContent{
    position:absolute;
    left:0;
    top:4px;
    color: #66C;
    font-family:"微软雅黑";
    white-space:nowrap;
}
</style>
<div id="Warnning">
    <div style="color: red;font-size: 15px; font-weight:bold;">请按人核件处理规范要求，登记人核件各环节时效：必须登记人核件寄出 。<div id="WarningContent">${noticeMsg } &nbsp;&nbsp;</div></div>
</div>
<script type="text/javascript">
if(!window.rollWord){
     var rollWord = {
           Warnning:document.getElementById("Warnning"),
           WarningContent:document.getElementById("WarningContent"),
           _containerWidth:1,
           _contentWidth:1,
           _speed:1,
           setSpeed:function(opt){
                 var This = this;
                 This._speed = opt;
           },
           setContainerWidth:function(){
                 var This = this;
                 This._containerWidth = This.Warnning.offsetWidth;
           },
           setContentWidth:function(){
                 var This = this;
                 This._contentWidth = This.WarningContent.offsetWidth;
           },
           roll:function(){
                 var This = this;
                 This.WarningContent.style.left = parseInt(This._containerWidth) + "px";
                 var time = setInterval(function(){This.move()},20);
                 This.Warnning.onmouseover = function(){
                      clearInterval(time);
                 };
                 This.Warnning.onmouseout = function(){
                      time = setInterval(function(){This.move()},20);
                 };
           },
           move:function(){
                 var This = this;
                 if(parseInt(This.WarningContent.style.left)+This._contentWidth > 0)
                 {
                      This.WarningContent.style.left = parseInt(This.WarningContent.style.left)-This._speed + "px";
                 }
                  else
                  {
                      This.WarningContent.style.left = parseInt(This._containerWidth) + "px";
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