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
	<link type="text/css" rel="stylesheet" href="../css/demo.css" />
		<link type="text/css" rel="stylesheet" href="../css/jquery.mmenu.css" />
	<link rel="shortcut icon" href="../img/favicon.ico"  type="text/css"/>
	<script src="../js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="../js/index.js" type="text/javascript"></script>
	<script src="../js/jquery.mobile-1.3.2.min.js" type="text/javascript"></script>
	<script src="../js/func_all.js" type="text/javascript"></script>  
	<script src="../js/hammer.js" type="text/javascript"></script>  
	<script type="text/javascript" src="../js/jquery.mmenu.js"></script>
	<script type="text/javascript" src="../js/nmmenu_hj.js"></script>
	<script src="../js/hj.js" type="text/javascript"></script>  
    <script language="javascript" type="text/javascript">
			
			function computeGuarantee_mian2(){
				//$("#customerinfoshow").text("success:"+customerinfo.join());
				//alert($("#payperiodmain").text());
				$("#showage_m2").text(i_age);

				f_mainpremium=i_premium*1;
				var i_copiesMain=f_mainpremium/1000;
			 
				f_maincoverage=((i_years==5)? arr_main2_5y[i_age]: arr_main2_10y[i_age])*i_copiesMain;
				
				f_annuity=((i_years==5)? (f_maincoverage*0.06): (f_maincoverage*0.1));//divided premium, 5year 6%;10year 10%
				 	
				$("#annuity_m2").text(formatNum(f_annuity));   //回显年金
				$("#annuity_m2_a").text(formatNum(f_annuity));   //回显年金
			 
					
				 		
				if(i_years==5){
					$("#maincoverage_m2").text(formatNum(f_maincoverage*1));     //回显基本保额			
					$("#totalpremium_m2").text(formatNum(i_premium*1));        //回显所交保费
					
					$("#unexpectedgomax_m2_a").text(formatNum(f_maincoverage*5));   //回显最高金额=三种缴费方式的基本保额*缴费年度*1倍
					$("#unexpectedgomax_m2_b").text(formatNum(f_maincoverage*2*5));   //回显最高金额=三种缴费方式的基本保额*缴费年度*2倍
					$("#unexpectedgomax_m2_c").text(formatNum(f_maincoverage*3*5));   //回显最高金额=三种缴费方式的基本保额*缴费年度*3倍
					$("#payperiodmain_m2").text(i_years+"年");
				}
				else
				{
					$("#maincoverage_m2").text(formatNum(f_maincoverage*1));     //回显基本保额			
					$("#totalpremium_m2").text(formatNum(i_premium*1));        //回显所交保费
					
					$("#unexpectedgomax_m2_a").text(formatNum(f_maincoverage*10));   //回显最高金额=三种缴费方式的基本保额*缴费年度*1倍
					$("#unexpectedgomax_m2_b").text(formatNum(f_maincoverage*2*10));   //回显最高金额=三种缴费方式的基本保额*缴费年度*2倍
					$("#unexpectedgomax_m2_c").text(formatNum(f_maincoverage*3*10));   //回显最高金额=三种缴费方式的基本保额*缴费年度*3倍
					$("#payperiodmain_m2").text(i_years+"年");
				}
				
				$("#totalpremium_m2_a").text(formatNum(i_premium*1));        //同一变量不回显,故重新赋值回显180天内所交保费
				
				f_totalpremium=((i_years==5)? (i_premium*5): (i_premium*10));
				$("#totalpremium_m2_all").text(formatNum(f_totalpremium*1));     //重新赋值回显仍然生存所交的总保费
				
				$("#unexpectedgomin_m2_a").text(formatNum(f_maincoverage));   //回显最低金额=三种缴费方式的基本保额*缴费1年
				$("#unexpectedgomin_m2_b").text(formatNum(f_maincoverage*2));   //回显最低金额=三种缴费方式的基本保额*缴费1年*2倍
				$("#unexpectedgomin_m2_c").text(formatNum(f_maincoverage*3));   //回显最低金额=三种缴费方式的基本保额*缴费1年*3倍
							 
				
			}	 /*mian2*/
			
		 
		$(document).on("pageinit", function() {		 
			$("#getguarantee_mian2").click(function(){
				//if premiuOK,renew & show page nianbguarantee
				i_money_check=$("#premium_input_m2").val();                    /*强行赋值,检查函数使用*/; 
				if(premiumOK_2()){
					i_age=$("#age_m2").val();
					//i_sex=$("#male").is(':checked') ? 1:0;
					//i_years=$("#fiveYear").is(':checked')? 5:10;
					i_years=$('input:radio[name="years_m2"]:checked').val();
					i_premium=$("#premium_input_m2").val(); //old var  is customerinfo[3]
					 
					computeGuarantee_mian2();
					$.mobile.changePage("#mian2guarantee");
					
				}
			});
		});  
	</script>
</head>
<body>
    
<!-- page mian2info  -->
<div data-role="page"  id="mian2info" class="info">
    <div data-role="content">
        <div class="customerinfo">
                    <div class="customertext">被保险人年龄（0至55岁）：</div>
                    <div class="divage"><input id="age_m2" type="range"  value="25" min="0" max="55" /></div><br/><hr/><br/>
                    <div class="customertext">交 费 期：</div>
                    <div class="ui-field-contain">
  						<fieldset data-role="controlgroup">	
                                <input type="radio" name="years_m2" id="5year_m2" value="5"/>
                                <label for="5year_m2" data-mini="true">5年</label>                                
                               	<input type="radio" name="years_m2" id="10year_m2" value="10" checked="checked" />  
                               	<label for="10year_m2" data-mini="true">10年</label>         
                    	</fieldset>
                    </div>
                    <div id="" class="ui-grid-a  smallinfoinput" >
                            <div class="ui-block-a">
                                </div>
                            <div class="ui-block-b"></div>
                    </div><hr/><br/>
                    <div class="customertext">年交费合计（要求为500的整数倍，2份起售，单位：元）：</div>
                    <input class="premium_input" id="premium_input_m2" type="number" data-mini="true" value="1000" min="1000" step="500"/>
                    <span class="premiumalert">本保险产品每份保费500元,最低2份起售,请输入大于1000且为500整倍数的数值！</span><br/><hr/>
		</div> 
        <div class="ui-block-a getguaranteediv"><div  data-role="button" id="getguarantee_mian2">计算保险利益</div></div><br/><br/>
    </div>
    <div class="oneblankdiv"></div>
    <div class="blankdiv"></div>
    <div data-role="footer" class="footer">
    	<div class="footertext">中邮保险销售辅助工具V3.0.1</div>
    </div>
</div> <!--End of page mian2info -->



<!--mian2 info  -->
<div data-role="page" id="mian2guarantee"  class="info">
    <div data-role="content">
    <div style=" position: absolute;line-height:1em;right:0.5%;top:0.5em;"><a   data-add-back-btn="true" style="font-size:1em;text_decoration:none;"   data-rel="back" data-icon="back" data-role="button" class="ui-btn-right" >返回</a></div><br/>
        <div><span class="title">产品亮点：</span> 绵绵寿2号<br/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 富寿绵长图个福!<br/><br/>
        </div>
        <div><span class="title">基本信息:</span>绵绵寿2号年金保险(分红型)；被保险人年龄<span id="showage_m2" class="parameter"> </span>岁
        	<div class="maininfotable">	        	
	    		 <table data-role="table"  class="ui-body-d ui-shadow table-stripe ui-responsive"  >
			         <thead>
			           <tr class="ui-bar-c"><th>交费期间</th><th>保险期间</th><th>所交保费</th><th >基本保额</th>
			           </tr>
			         </thead>
			         <tbody>          
			           <tr>	             
			             <td><span id="payperiodmain_m2" class="parameter"> </span></td>
			             <td><span class="parameter">15年 </span></td>
			             <td><span id="totalpremium_m2" class="parameter"> </span></td>
			             <td><span id="maincoverage_m2" class="parameter"> </span></td>	             
			           </tr>	           
			         </tbody>
		       </table> 
	       </div>
	    </div>    		 
		<div>
        	 <div class="title">一、保险责任：</div>
			 <div class="title2">(一)生存年金</div>
             <div class="coveragetext">保险期内被保险人生存，每年可领取"生存年金"<span class="coverage" id="annuity_m2_a"></span>元。</div>
			  <div class="title2">(二)满期保险金</div>
             <div class="coveragetext">保险合同期满，无息返还已交保险费<span class="coverage" id="totalpremium_m2_all"></span>元，本合同效力终止。</div>
             <div class="title2">(三)身故、全残保险金金</div>
             <div class="coveragetext">被保险人于合同生效之日起180日后因疾病导致身故或全残，按照基本保额乘以交费年度数给付"身故、全残保险金"，最低给付<span class="coverage" id="unexpectedgomin_m2_a"></span>元，最高给付<span class="coverage" id="unexpectedgomax_m2_a"></span>元，合同效力终止。</div>
             <div class="title2">(四)意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起因意外导致身故或全残，按照基本保额乘以交费年度数的<span  class="parameter">2倍</span>给付"身故、全残保险金"，最低给付<span class="coverage" id="unexpectedgomin_m2_b"></span>元，最高给付<span class="coverage" id="unexpectedgomax_m2_b"></span>元，本合同效力终止。</div>   
			 <div class="title2">(五)交通意外身故、全残保险金</div>
             <div class="coveragetext">被保险人于合同生效之日起搭乘指定公共交通工具（见合同条款）期间遭受意外伤害事故导致身故或全残，按照基本保额乘以交费年度数的<span  class="parameter">3倍</span>给付"交通意外身故、全残保险金"，最低给付<span class="coverage" id="unexpectedgomin_m2_c"></span>元，最高给付<span class="coverage" id="unexpectedgomax_m2_c"></span>元，本合同效力终止。</div><br/>  
			<div class="title">二、合同权益</div>
             <div class="title2">保单红利</div>
            <div class="coveragetext">可对分红选择累积生息，享受高速发展的中邮保险的经营成果。</div> <br/> 
			<div class="alert">本计划书仅供参考，详细内容以保险合同条款为准。</div><hr/>
			</div>
		</div>
		<div data-role="footer" class="footer">
			<div class="footertext">中邮保险销售辅助工具V3.0.1</div>
		</div>
</div><!-- end of mian2 guarantee -->

</body>
</html>