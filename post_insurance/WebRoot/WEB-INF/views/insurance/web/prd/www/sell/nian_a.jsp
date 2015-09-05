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
	<link rel="shortcut icon" href="../img/favicon.ico"  type="text/css"/>
	<link type="text/css" rel="stylesheet" href="../css/demo.css" />
	<link type="text/css" rel="stylesheet" href="../css/jquery.mmenu.css" />
	<script src="../js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="../js/index.js" type="text/javascript"></script>
	<script src="../js/jquery.mobile-1.3.2.min.js" type="text/javascript"></script>
	<script src="../js/func_all.js" type="text/javascript"></script>  
	<script src="../js/hammer.js" type="text/javascript"></script>  
	<script src="../js/hj.js" type="text/javascript"></script> 
    <script language="javascript" type="text/javascript">
			
			 
			
			function computeGuarantee_n_a(){
				//$("#customerinfoshow").text("success:"+customerinfo.join());
				//alert($("#payperiodmain").text());
				 $("#showage_n_a").text(i_age);
				//coverages used in table
				
				
				var i_copiesMain=i_premium/1000;				 
				var i_n_a=9;
				
				if(i_age<=59)
				{
					i_n_a=0;				
				}
				else
				{	
					if((i_age>=60)&&(i_age<=63))
					{
						i_n_a=1;
					}
					else
					{
						i_n_a=2;
					}
				}
				
				
				f_maincoverage_n_a=arr_n_a[i_n_a]*i_copiesMain;
				 
				$("#mainpremium_n_a").text(formatNum(i_premium*1));                     //所交保费
				$("#maincoverage_n_a").text(formatNum(f_maincoverage_n_a));              //基本保额
						
				$("#termination_n_a").text(formatNum(f_maincoverage_n_a*1));       //满期保险金
				$("#disease_n_a").text(formatNum(i_premium*1.05));                          //重疾
				$("#unexpected_n_a").text(formatNum(i_premium*2));                    //意外身故
				
				/*
				f_cash_value1=arr_n_a_cash1[i_age]*i_copiesMain;
				f_cash_value2=arr_n_a_cash2[i_age]*i_copiesMain;
				f_cash_value3=arr_n_a_cash3[i_age]*i_copiesMain;
				f_cash_value4=arr_n_a_cash4[i_age]*i_copiesMain;
				
				$("#cash_value1").text(formatNum(f_cash_value1*1));       //现金价值
				$("#cash_value2").text(formatNum(f_cash_value2*1));       //现金价值
				$("#cash_value3").text(formatNum(f_cash_value3*1));       //现金价值
				$("#cash_value4").text(formatNum(f_cash_value4*1));       //现金价值
				$("#cash_value5").text(formatNum(f_maincoverage_n_a*1));       //第五年现金价值等于满期保险金
				
				f_total_proceeds1=(arr_n_a_cash1[i_age]-1000)/1000;
				f_total_proceeds2=(arr_n_a_cash2[i_age]-1000)/1000;
				f_total_proceeds3=(arr_n_a_cash3[i_age]-1000)/1000;
				f_total_proceeds4=(arr_n_a_cash4[i_age]-1000)/1000;
				f_total_proceeds5=(arr_n_a[i_n_a]-1000)/1000;
				
				$("#total_proceeds1").text(f_total_proceeds1);       //累积年化收益率
				$("#total_proceeds2").text(f_total_proceeds2);       //累积年化收益率
				$("#total_proceeds3").text(f_total_proceeds3);       //累积年化收益率
				$("#total_proceeds4").text(f_total_proceeds4);       //累积年化收益率
				$("#total_proceeds5").text(f_total_proceeds5);       //累积年化收益率
				
				$("#total_proceeds1").text(f_total_proceeds1/1);       //累积年化收益率
				$("#total_proceeds2").text(f_total_proceeds2/2);       //累积年化收益率
				$("#total_proceeds3").text(f_total_proceeds3/3);       //累积年化收益率
				$("#total_proceeds4").text(f_total_proceeds4/4);       //累积年化收益率
				$("#total_proceeds5").text(f_total_proceeds5/5);       //累积年化收益率
			  
				*/
				
				var $profitTableItems=$('#nianaProfitTable span');				
				for(j=0;j<4;j++){					
					var k=j*3;					
					var cash=arr_n_a_cash[(i_age*1)+j*66];
					var f_cash_value=cash*i_copiesMain;
					var f_total_proceeds=cash-1000;           //现金价值以1000为单位的收益部分
					var f_avg_proceeds=Math.round((cash-1000)*10/(j+1));     //留到个位四舍五入确保最后为百分比保留两位
					
			   		$profitTableItems.eq(k++).text(formatNum(f_cash_value));
			   		$profitTableItems.eq(k++).text(f_total_proceeds/10+"%");  // 因为是固定比例,所以直接用现金价值以1000为单位的收益部分再除以1000再乘以100
			   		$profitTableItems.eq(k++).text(f_avg_proceeds/100+"%");			   		
			   	}
				
				f_total_proceeds5=arr_n_a[i_n_a]-1000;
				f_avg_proceeds=Math.round(f_total_proceeds5*10/5);      //累积扩大一百倍除以年度再四舍五入
				$profitTableItems.eq(12).text(formatNum(f_maincoverage_n_a*1)); //第五年现金价值等于满期保险金
				$profitTableItems.eq(13).text(f_total_proceeds5/10+"%"); //因为是固定比例,所以直接用满期保险金单位减去1000后再除以1000再乘以100
				$profitTableItems.eq(14).text(f_avg_proceeds/100+"%"); //第5年特殊处理
			  
			}		/*新年A测试暂时注销*/ 		

			
		 
		$(document).on("pageinit", function() {		 
			$("#getguarantee_n_a").click(function(){
				//if premiuOK,renew & show page nianbguarantee
				//premium=premium_input_f1;
				 
				 i_money_check=$("#premium_input_n_a").val();                    /*强行赋值,检查函数使用*/;
				if(premiumOK_20()){
					i_age=$("#age_n_a").val();
					i_premium=$("#premium_input_n_a").val(); //old var  is customerinfo[3]
					
					computeGuarantee_n_a();
					$.mobile.changePage("#n_a_guarantee");
					
				}
			});
		});  
	</script>
</head>
<body>
    
<!-- page nian_a_info   -->
<div data-role="page"  id="nian_a_info" class="info">
    <div data-role="content">
        <div class="customerinfo">
        	<div class="customertext">投保信息</div>
                    <div class="customertext">被保险人年龄（0至65岁）：</div>
                    <div class="divage"><input id="age_n_a" type="range"  value="25" min="0" max="65" /></div><br/><hr/><br/>
                    <div class="customertext">交费方式：趸交</div><hr/><br/>
                    <div class="customertext">年交费合计（要求为1000的整数倍，单位：元）：</div>
                    <input class="premium_input" id="premium_input_n_a" type="number" data-mini="true" value="20000" min="20000" step="1000"/>
                    <span class="premiumalert">本保险产品每份保费1000元,最低20份起售,请输入大于20000且为1000整倍数的数值！</span><br/><hr/>
		</div> 
        <div class="ui-block-a getguaranteediv"><div  data-role="button" id="getguarantee_n_a">计算保险利益</div></div><br/><br/>
    </div>
</div> <!--End of page nian_a_info -->



<!----- page nian_a_info -->
<div data-role="page" id="n_a_guarantee"  class="info">
    <div data-role="content">
    <div style=" position: absolute;line-height:1em;right:0.5%;top:0.5em;"><a   data-add-back-btn="true" style="font-size:1em;text_decoration:none;"   data-rel="back" data-icon="back" data-role="button" class="ui-btn-right" >返回</a></div><br/>
        <div><span class="title">产品亮点：</span> 年年好新A款<br/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  保本更好 保障更好 保单更好 <br/><br/>
        </div>
        <div><span class="title">基本信息:</span>被保险人年龄<span id="showage_n_a" class="parameter"> </span>岁
    		 <div class="ui-grid-d">
            	<div class="ui-block-a tablehead">保险名称</div>
                <div class="ui-block-c tablehead">保险期间</div>
                <div class="ui-block-d tablehead">所交保费</div>
                <div class="ui-block-e tablehead">基本保额</div>
                
                <div class="ui-block-a table tablecontent">年年好新A款两全保险</div>
                <div class="ui-block-c table tablecontent"><span class="parameter">5年 </span></div>
                <div class="ui-block-d table tablecontent"><span id="mainpremium_n_a" class="parameter"> </span></div>
                <div class="ui-block-e table tablecontent"><span id="maincoverage_n_a" class="parameter"> </span></div>             
			</div>
			<span class="title">收益表:</span>
    		 <div class="ui-grid-d" id="nianaProfitTable">
            	<div class="ui-block-a tablehead">年限</div>
                <div class="ui-block-c tablehead">现金价值</div>
                <div class="ui-block-d tablehead">累积收益</div>
                <div class="ui-block-e tablehead">年化收益</div>
                
                <div class="ui-block-a table_a tablecontent">第一年</div>
                <div class="ui-block-c table_a tablecontent"><span id="cash_value1" class="parameter"> </span></div>
                <div class="ui-block-d table_a tablecontent"><span id="total_proceeds1" class="parameter"> </span></div>
                <div class="ui-block-e table_a tablecontent"><span id="" class="parameter"> </span></div>

				<div class="ui-block-a table_a tablecontent">第二年</div>
                <div class="ui-block-c table_a tablecontent"><span id="cash_value2" class="parameter">  </span></div>
                <div class="ui-block-d table_a tablecontent"><span id="total_proceeds2" class="parameter"> </span></div>
                <div class="ui-block-e table_a tablecontent"><span id="" class="parameter"> </span></div>
				
				<div class="ui-block-a table_a tablecontent">第三年</div>
                <div class="ui-block-c table_a tablecontent"><span id="cash_value3" class="parameter">  </span></div>
                <div class="ui-block-d table_a tablecontent"><span id="total_proceeds3" class="parameter"> </span></div>
                <div class="ui-block-e table_a tablecontent"><span id="" class="parameter"> </span></div>
				
				<div class="ui-block-a table_a tablecontent">第四年</div>
                <div class="ui-block-c table_a tablecontent"><span id="cash_value4" class="parameter">  </span></div>
                <div class="ui-block-d table_a tablecontent"><span id="total_proceeds4" class="parameter"> </span></div>
                <div class="ui-block-e table_a tablecontent"><span id="" class="parameter"> </span></div>
				
				<div class="ui-block-a table_a tablecontent">第五年</div>
                <div class="ui-block-c table_a tablecontent"><span id="cash_value5" class="parameter">  </span></div>
                <div class="ui-block-d table_a tablecontent"><span id="total_proceeds5" class="parameter"> </span></div>
                <div class="ui-block-e table_a tablecontent"><span id="" class="parameter"> </span></div>
			</div>		
			<div>
        	 <div class="title">保险责任：</div>
			 <div class="title2">(一)满期保险金</div>
             <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="termination_n_a"></span>元，本合同效力终止。</div>
             <div class="title2">(二)身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起因疾病导致身故或全残，按已交保险费的105%给付"身故、全残保险金" <span class="coverage" id="disease_n_a"></span>元，合同效力终止。</div>
             <!--<div class="alert">&nbsp;&nbsp;&nbsp;被保险人于合同生效之日起180日内(含第180日)因疾病身故或全残，无息返还已交的保险费<span class="coverage" id=" "></span>元,本合同效力终止。</div>  -->
             <div class="title2">(三)意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起因意外导致身故或全残，按已交保费的<span id="showsex" class="parameter">2倍</span>给付"意外身故、全残保险金"<span class="coverage" id="unexpected_n_a"></span>元，本合同效力终止。</div> <br/>
            <div class="title">合同权益：</div>
             <div class="title2">保单借款</div>
            <div class="coveragetext">最高可以借保单现金价值的90%。</div>      <br/>    		
			<div class="alert">本计划书仅供参考，详细内容以保险合同条款为准。</div><hr/>
			</div>
		</div>
	</div>
</div><!-- end of nian_a guarantee -->


</body>
</html>