<!DOCTYPE html> 
<html>
<head>
	<title>中邮保险产品小助手</title>
    <meta charset="utf-8" />
    <meta name="format-detection" content="telephone=no" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="../css/jquery.mobile-1.3.2.min.css"  type="text/css" />
    <link rel="stylesheet" href="../css/jqm-demos.css"  type="text/css"/>
    <link rel="stylesheet" href="../css/products.css"  type="text/css"/>	
	<link rel="stylesheet" href="../css/demo.css"  type="text/css"/>
	
	<script src="../js/jquery-1.11.0.min.js" type="text/javascript"></script>	
	<script src="../js/jquery.mobile-1.3.2.min.js" type="text/javascript"></script>
	<script src="../js/func_all.js" type="text/javascript"></script> 
	<script src="../js/hj.js" type="text/javascript"></script> 	
	
    <script  type="text/javascript">	
			/*富1测试暂时注销*/
			function computeGuarantee_fu(){
				//$("#customerinfoshow").text("success:"+customerinfo.join());
				 // alert($("#payperiodmain").text());
				 $("#showage_f1").text(i_age);
				//coverages used in table
				
				
				var i_copiesMain=i_premium/1000;
				 
				if(i_age<=25)
				{
					f_maincoverage_f1=arr_fu1[0]*i_copiesMain;				
				}
				else
				{	
					if((i_age>=26)&&(i_age<=35))
					{
						f_maincoverage_f1=arr_fu1[1]*i_copiesMain;	
					}
					else
					{
						if((i_age>=36)&&(i_age<=45))
						{
							f_maincoverage_f1=arr_fu1[2]*i_copiesMain;
						}
						else
						{
							if((i_age>=46)&&(i_age<=55))
							{
								f_maincoverage_f1=arr_fu1[3]*i_copiesMain;
							}
							else
							{
								f_maincoverage_f1=arr_fu1[4]*i_copiesMain;
							}
						}
					}
				}
				 
				
				$("#mainpremium_f1").text(formatNum(i_premium*1));
				
				$("#maincoverage_f1").text(formatNum(f_maincoverage_f1));
				 
				var f_gomax_f1=f_maincoverage_f1*2;
				$("#unexpectedgomax_f1").text(formatNum(f_gomax_f1));
				
				$("#termination_f1").text($("#maincoverage_f1").text());
				$("#termination_f1_a").text($("#maincoverage_f1").text());
				$("#mainpremium_f1_a").text($("#mainpremium_f1").text());
				//alter("b");
			}		/*富1测试暂时注销*/ 
			
	 
		 $(document).on("pageinit", function() {		 
				//富1测试暂时注销   
			$("#getguarantee_fu").click(function(){
				//if premiuOK,renew & show page nianbguarantee
				//premium=premium_input_f1;
				 
				 i_money_check=$("#premium_input_f1").val();                    //强行赋值,检查函数使用 
				if(premiumOK()){
					i_age=$("#age_f1").val();
					//i_sex=$("#male").is(':checked') ? 1:0; 
					//i_years=$("#threeYear").is(':checked')? 3:5;
					i_premium=$("#premium_input_f1").val(); //old var  is customerinfo[3]
					 
					computeGuarantee_fu();
					//alter("a");
					 $.mobile.changePage( "#fu1guarantee" );
					 
					
				}
			});
		 }); 
	</script>
</head>
<body>
    
 
<!-- page Fu1info   -->
<div data-role="page"  id="fu1info" class="info">
    <div data-role="content">
        <div class="customerinfo">
        	<div class="customertext">基本信息：富富余1号两全保险(分红型)</div>
                    <div class="customertext">被保险人年龄（0至65岁）：</div>
                    <div class="divage"><input id="age_f1" type="range"  value="25" min="0" max="65" /></div><br/><hr/><br/>
                    <div class="customertext">交费方式：趸交</div><hr/><br/>
                    <div class="customertext">年交费合计（要求为1000的整数倍，单位：元）：</div>
                    <input class="premium_input" id="premium_input_f1" type="number" data-mini="true" value="1000" min="1000" step="1000"/>
                    <span class="premiumalert">本保险产品每份保费1000元，请输入大于1000且为1000整倍数的数值！</span><br/><hr/>
		</div> 
        <div class="ui-block-a getguaranteediv"><div  data-role="button" id="getguarantee_fu">计算保险利益</div></div><br/><br/>
 
    </div>
    <div class="oneblankdiv"></div>
    <div class="blankdiv"></div>
    <div data-role="footer" class="footer">
    	<div class="footertext">中邮保险销售辅助工具V3.0.2</div>
    </div>
	
</div> <!--End of page fu1info -->
<!----- page Fu1 guarantee --> 
<div data-role="page" id="fu1guarantee"  class="info">
    <div data-role="content">
	 <div style=" position: absolute;line-height:1em;right:0.5%;top:0.5em;"><a   data-add-back-btn="true" style="font-size:1em;text_decoration:none;"   data-rel="back" data-icon="back" data-role="button" class="ui-btn-right" >返回</a></div><br/>
        <div><span class="title">产品亮点：</span> 富富余1号<br/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 分红生财图个富!<br/><br/>
        </div>
        <div><span class="title">基本信息:</span>富富余1号;&nbsp;&nbsp;被保险人年龄<span id="showage_f1" class="parameter"> </span>岁    		 
        	<div class="maininfotable">	        	
	    		 <table data-role="table"  class="ui-body-d ui-shadow table-stripe ui-responsive"  >
			         <thead>
			           <tr class="ui-bar-b"><th>保险期间</th><th>所交保费</th><th >基本保额</th>
			           </tr>
			         </thead>
			         <tbody>          
			           <tr>	             
			             <td><span class="parameter">5年 </span></td>
			             <td><span id="mainpremium_f1" class="parameter"> </span></td>
			             <td><span id="maincoverage_f1" class="parameter"> </span></td>			             
			           </tr>	           
			         </tbody>
		       </table> 
	       </div>
		</div>
			<div>
        	 <div class="title">一、保险责任：</div>
			 <div class="title2">(一)满期保险金</div>
             <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="termination_f1"></span>元，本合同效力终止。</div>
             <div class="title2">(二)身故、全残保险金金</div>
			 <div class="coveragetext">被保险人在保险责任期间内，因非意外原因导致身故或全残，我公司将按本合同约定的基本保险金额给付"身故、全残保险金" <span class="coverage" id="termination_f1_a"></span>元，合同效力终止。</div>
             <!-- <div class="coveragetext">被保险人于合同生效之日起180日后因疾病导致身故或全残，给付"身故、全残保险金" <span class="coverage" id="termination_f1_a"></span>元，合同效力终止。</div>
             <div class="alert">&nbsp;&nbsp;&nbsp;被保险人于合同生效之日起180日内(含第180日)因疾病身故或全残，无息返还已交的保险费<span class="coverage" id="mainpremium_f1_a"></span>元,本合同效力终止。</div>     20150723取消--->
             <div class="title2">(三)意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起因意外导致身故或全残，按照基本保额的<span id="showsex" class="parameter">2倍</span>给付"意外身故、全残保险金"<span class="coverage" id="unexpectedgomax_f1"></span>元，本合同效力终止。</div> <br/>
            <div class="title">二、合同权益</div>
             <div class="title2">保单红利</div>
            <div class="coveragetext">可对分红选择累积生息，享受高速发展的中邮保险的经营成果。</div> <br/> 
			<div class="alert">本计划书仅供参考，详细内容以保险合同条款为准。</div><hr/>
             <div class="manger">理财经理：<span style="padding-left:6em">服务电话：</span></div><br/><br/>
			</div>
		</div>
		<br/><br/>		
		
		<div data-role="footer" class="footer">
			<div class="footertext">中邮保险销售辅助工具V3.0.2</div>
		</div>		
</div><!-- end of fu1 guarantee -->
</body>
</html>