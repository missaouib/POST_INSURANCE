<!DOCTYPE html> 
<html>
<head>
	<title>中邮保险产品小助手</title>
    <meta charset="utf-8" />
    <meta name="format-detection" content="telephone=no" />
	 <meta name="viewport" content="width=device-width, initial-scale=1" /> 
	<!--<meta name="viewport" content="width=device-width, initial-scale=1.0 " />-->
	<link rel="stylesheet" href="../css/jquery.mobile-1.3.2.min.css"  type="text/css" />
    <link rel="stylesheet" href="../css/jqm-demos.css"  type="text/css"/>
    <link rel="stylesheet" href="../css/products.css"  type="text/css"/>	
	<link rel="stylesheet" href="../css/demo.css"  type="text/css"/>
	
	<script src="../js/jquery-1.11.0.min.js" type="text/javascript"></script>	
	<script src="../js/jquery.mobile-1.3.2.min.js" type="text/javascript"></script>
	<script src="../js/func_all.js" type="text/javascript"></script> 
	<script src="../js/hj.js" type="text/javascript"></script>
    <script language="javascript" type="text/javascript">
			
			function computeGuarantee(){
				//$("#customerinfoshow").text("success:"+customerinfo.join());
				//alert($("#payperiodmain").text());
				$("#showage").text(i_age);
				if(i_sex==1){
					$("#showsex").text("男");
				}
				else{
					$("#showsex").text("女");
				}
				$("#payperiodmain").text(i_years+"年");
				$("#payperiodaddtional").text(i_years+"年");
				//coverages used in table
				
				f_mainpremium=((i_years==3)? (i_premium*0.92): (i_premium*0.95));//divided premium, 3year-92% 8%;5year 95% 5%
				f_additionalpremium=((i_years==3)? (i_premium*0.08): (i_premium*0.05));				
				$("#mainpremium").text(formatNum(f_mainpremium));
				$("#additionalpremium").text(formatNum(f_additionalpremium));
				
				var i_copiesMain=f_mainpremium/1000;
				var i_copiesAdditional=f_additionalpremium/10;
				f_maincoverage=((i_years==3)? arr_mainCoverage3y[i_age]: arr_mainCoverage5y[i_age])*i_copiesMain;				
				$("#maincoverage").text(formatNum(f_maincoverage));
				if(i_sex==1){ //computer additional coverage according to sex and insurance period
					//男
					f_additionalcoverage=((i_years==3)? arr_additionalCoverageMale3y[i_age]: arr_additionalCoverageMale5y[i_age])
											*i_copiesAdditional;
				}
				else{
					//女
					f_additionalcoverage=((i_years==3)? arr_additionalCoverageFemale3y[i_age]: arr_additionalCoverageFemale5y[i_age])
											*i_copiesAdditional;
				}
				$("#additionalcoverage").text(formatNum(f_additionalcoverage));
				
				$("#totalpremium").text(formatNum(i_premium*1));
				//coverages used in text 
				$("#seriousillness").text($("#additionalcoverage").text());
				
				var f_gomin=(i_age<18) ? f_mainpremium+f_additionalpremium : f_mainpremium+f_additionalcoverage ;
				$("#gomin").text(formatNum(f_gomin));
				var f_gomax=(i_age+INSURANCEPERIOD > 18) ?  f_mainpremium*i_years+f_additionalcoverage
								 : (f_mainpremium+f_additionalpremium)*i_years;
				$("#gomax").text(formatNum(f_gomax));
				
				$("#unexpectedgomin").text($("#gomin").text());
				$("#unexpectedgomax").text($("#gomax").text());
				
				$("#termination").text($("#maincoverage").text());
				var f_annuity=f_mainpremium*i_years*0.011;
				//count annuity and it's profit,interest rate use 3.5% 1+3.5%=1.035
				$("#annuity").text(formatNum(f_annuity));
				var f_annuityandprofit=0;
				if(i_years==3){
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3))*Math.pow(1.035,10-3);
				}
				else{
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3)
													+Math.pow(1.035,4)+Math.pow(1.035,5))*Math.pow(1.035,10-5);
				}
				$("#annuityandprofit").text(formatNum(f_annuityandprofit));
			}		/*年B部分暂时注销测试*/			

			
		 
		$(document).on("pageinit", function() {		 
			$("#getguarantee").click(function(){
			//alert("B");
				//if premiuOK,renew & show page nianbguarantee
				i_money_check=$("#premium_input_nb").val();                    /*强行赋值,检查函数使用*/;
				if(premiumOK()){
					i_age=$("#age").val();
					i_sex=$("#male").is(':checked') ? 1:0;
					i_years=$("#threeYear").is(':checked')? 3:5;
					i_premium=$("#premium_input_nb").val(); //old var  is customerinfo[3]
					
					computeGuarantee();
					$.mobile.changePage("#nianbguarantee");					
				}
			});	
		});  
	</script>
</head>
<body>
    
<!-- page NianBinfo    -->
<div data-role="page"  id="nianbinfo" class="info">
    <div data-role="content">
        <div class="customerinfo">
                    <div class="customertext">被保险人年龄（0至55岁）：</div>
                    <div class="divage"><input id="age" type="range"  value="25" min="0" max="55" /></div><br/><hr/><br/>
                    <div class="customertext">被保险人性别：</div>
					 <div class="ui-field-contain">
  						<fieldset data-role="controlgroup">	
                                <input type="radio" name="sex" id="male" value="male"  />
                                <label for="male" data-mini="true">男</label>
                               	<input type="radio" name="sex" id="female" value="female" checked="checked" />
								<label for="female" data-mini="true">女</label>
                    	</fieldset>
                    </div>
					
                    <hr/><br/>
                    <div class="customertext">交 费 期：</div>
                    <div class="ui-field-contain">
  						<fieldset data-role="controlgroup">	
                                <input type="radio" name="years" id="threeYear" value="3"  />
                                <label for="threeYear" data-mini="true">3年</label>
                               	<input type="radio" name="years" id="fiveYear" value="5" checked="checked" />
								<label for="fiveYear" data-mini="true">5年</label>
                    	</fieldset>
                    </div>
                    <hr/><br/>
                    <div class="customertext">年交费合计（要求为1000的整数倍，单位：元）：</div>
                    <input class="premium_input" id="premium_input_nb" type="number" data-mini="true" value="1000" min="1000" step="1000"/>
                    <span class="premiumalert">本保险产品每份保费1000元，请输入大于1000且为1000整倍数的数值！</span><br/><hr/>
		</div> 
        <div class="ui-block-a getguaranteediv"><div  data-role="button" id="getguarantee">计算保险利益</div></div><br/><br/>
    </div>
    <div class="oneblankdiv"></div>
    <div class="blankdiv"></div>
    <div data-role="footer" class="footer">
    	<div class="footertext">中邮保险销售辅助工具V3.0.1</div>
    </div>
</div> <!--End of page nianBinfo -->



<!-- page NianBinfo     -->
<div data-role="page" id="nianbguarantee"  class="info">
    <div data-role="content">
    <div style=" position: absolute;line-height:1em;right:0.5%;top:0.5em;"><a   data-add-back-btn="true" style="font-size:1em;text_decoration:none;"   data-rel="back" data-icon="back" data-role="button" class="ui-btn-right" >返回</a></div><br/>
        <div><span class="title">产品亮点：</span> 年年好保你好，重疾保障程度高<br/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生存金早早领，复利收益乐陶陶!<br/><br/>
        </div>
        <div><span class="title">基本信息:</span>年年好重疾保障计划;&nbsp;&nbsp;被保险人年龄<span id="showage" class="parameter">25</span>岁,&nbsp;&nbsp;
        		性别<span id="showsex" class="parameter">女</span>    		 
        	<div class="maininfotable">	        	
	    		 <table data-role="table"  class="ui-body-d ui-shadow table-stripe ui-responsive"  >
			         <thead>
			           <tr class="ui-bar-d"><th>保险名称</th><th>交费期间</th><th>保险期间</th><th>所交保费</th><th >基本保额</th>
			           </tr>
			         </thead>
			         <tbody>          
			           <tr>	             
			             <td><span class="parameter">年年好B</span></td>
			             <td><span id="payperiodmain" class="parameter">5年</span></td>
			             <td><span class="parameter">10年</span></td>
			             <td><span id="mainpremium" class="parameter">9500元</span></td>
			             <td><span id="maincoverage" class="parameter">50492元</span></td>	             
			           </tr>	           
			           <tr>
			           	<td><span class="parameter">中邮附加重大疾病保险</span></td>
			           	<td><span id="payperiodaddtional" class="parameter">5年</span></td>
			           	<td><span class="parameter">10年</span></td>
			           	<td><span id="additionalpremium" class="parameter">500元</span></td>
			           	<td><span id="additionalcoverage" class="parameter">131400元</span></td>
			           </tr>
			           <tr>
			           	<td><span class="parameter">年交费合计：</span></td>
			           	<td>&nbsp;</td>
			           	<td>&nbsp;</td>
			           	<td><span id="totalpremium" class="parameter">10000元</span></td>
			           	<td>&nbsp;</td>
			           </tr>
			         </tbody>
		       </table> 
	       </div> 
		</div>
        <div>
        	 <div class="title">一、保险责任：</div>
             <div class="title2">(一)重大疾病保险金</div>
             <div class="coveragetext">自本合同生效之日起180天后，被保险人经我公司认可的医院，由专科医生诊断初患本合同约定的“重大疾病”，给付重疾保障金<span class="coverage" id="seriousillness"></span>元，附加重大疾病保险责任终止，主险责任继续有效。</div>
             <div class="title2">(二)疾病身故、全残保险金金</div>
             <div class="coveragetext">被保险人于本合同生效之日起180天后因疾病导致身故或全残，最低给付金额<span class="coverage" id="gomin"></span>元，最高给付金额<span class="coverage" id="gomax"></span>元，本合同效力终止。</div>
            <div class="alert">自保险责任开始日或自最近的合同效力恢复日(以较迟着为准)起180日内(含第180日)因疾病身故或全残的，按无息的已交保险费金额给付”身故保险金”或”全残保险金”,本合同效力终止。</div>
             <div class="title2">(三)意外身故、全残保险金</div>
            <div class="coveragetext">被保险人于合同生效之日起因意外导致身故或全残，最低给付额<span class="coverage" id="unexpectedgomin"></span>元，最高给付金额<span class="coverage" id="unexpectedgomax"></span>元，本合同效力终止。</div>
             <div class="title2">(四)满期保险金</div>
            <div class="coveragetext">保险合同期满，领取满期金<span class="coverage" id="termination"></span>元，本合同效力终止。</div>
             <div class="title2">(五)生存年金</div>
             <div class="coveragetext"> 在交费期间内，被保险人生存，每年可领取生存年金<span class="coverage" id="annuity"></span>元，若选择累积生息，按3.5%累积利率估算保险期满可领取年金及收益<span class="coverage" id="annuityandprofit"></span>元。</div>
            <div class="title">二、合同权益</div>
             <div class="title2">(一)保单红利</div>
            <div class="coveragetext">可对分红选择累积生息，享受高速发展的中邮保险的经营成果。</div>
             <div class="title2">(二)豁免保险费</div>
            <div class="coveragetext">交费期间内，投保人因遭受意外伤害导致身故或全残，且意外伤害时不满65周岁的，则免交以后各期保险费，本合同继续有效。</div>
             <div class="title2">(三)保单借款</div>
            <div class="coveragetext">最高可以借保单现金价值的90%。</div>      <br/>    			
            <div class="alert">本计划书仅供参考，详细内容以保险合同条款为准。</div><hr/>
        </div>
		</div>
		<br><br>
		<div data-role="footer" class="footer">
			<div class="footertext">中邮保险销售辅助工具V3.0.1</div>
		</div>
 
</div><!-- end of nianB guarantee -->
</body>
</html>