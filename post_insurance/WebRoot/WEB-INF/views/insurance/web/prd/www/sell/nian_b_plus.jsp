<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
<!--
$("#background,#progressBar").hide();
//-->
</script>
	<link rel="stylesheet" href="/css/jquery.mobile-1.3.2.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/jqm-demos.css"  type="text/css"/>
    <link rel="stylesheet" href="/css/products.css"  type="text/css"/>	
	<link rel="stylesheet" href="/css/demo.css"  type="text/css"/>
	
	<script src="/js/jquery-1.11.0.min.js" type="text/javascript"></script>	
	<script src="/js/jquery.mobile-1.3.2.min.js" type="text/javascript"></script>
	<script src="/js/func_all.js" type="text/javascript"></script> 
	<script src="/js/hj.js" type="text/javascript"></script>


<!-- page ddb_info   -->
<div data-role="page"  id="nianbinfo" class="info">
    <div data-role="content">
        <div class="customerinfo">
              <div class="customertext">交费及保障期：</div>
              <div class="ui-field-contain">
				<fieldset data-role="controlgroup" data-type="horizontal">	
                          
                          <input type="radio" name="years_nb" id="threeYear_a" value="3a"  />
                          <label for="threeYear_a" data-mini="true">交3年保15年</label>
                          
                          <input type="radio" name="years_nb" id="threeYear_b" value="3b"  />
                          <label for="threeYear_b" data-mini="true">交3年保20年</label>
                          
                          <input type="radio" name="years_nb" id="threeYear_c" value="3c" />
                          <label for="threeYear_c" data-mini="true"> 交3年保至70岁</label>    

                          <input type="radio" name="years_nb" id="fiveYear_a" value="5a"   />
                          <label for="fiveYear_a" data-mini="true">交5年保15年</label>
                          
                          <input type="radio" name="years_nb" id="fiveYear_b" value="5b"  />
                          <label for="fiveYear_b" data-mini="true">交5年保20年</label>
                          
                          <input type="radio" name="years_nb" id="fiveYear_c" checked="checked" value="5c" />
                          <label for="fiveYear_c" data-mini="true"> 交5年保至70岁</label>      
              	</fieldset>                 	
              </div>	
              <hr/><br/>
			   <div class="customertext">被保险人性别</div>
						<div class="ui-field-contain" >
	  						<fieldset data-role="controlgroup" data-type="horizontal" >	
                                <input type="radio" name="sex" id="male" value="male"  />
                                <label for="male"  data-mini="true">男</label>
                               	<input type="radio" name="sex" id="female" value="female" checked="checked" />
								<label for="female"  data-mini="true">女</label>
	                    	</fieldset>
	                  </div>
				<span  class="divage_bbb_40"><div class="customertext" id="explain_nb" >被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：</div> 
				<input id="age" type="range"  value="35" min="0" max="55" /></span><br/><hr/><br/>
                 <div class="customertext">主险年交费合计（要求为1000的整数倍，1份起售，单位：元）：</div>
                 <input class="premium_input" id="premium_input_nb" type="number" data-mini="true" value="1000" min="1000" step="1000"/>
                 <span class="premiumalert">提示:本款保险产品每份保费1000元,最低1份起售,<span span id="remind_bbb"></span>大于1000且为1000整倍数的数值!(工具仅做自动核保范围内的演示)</span><br/><hr/>
				<div class="customertext">附加险年交费合计（每份10元，要求为10的整数倍，1份起售，单位：元）：</div>
                 <input class="premium_input1" id="premium_input_nb2" type="number" data-mini="true" value="10" min="10" step="10"/>
                 <span class="premiumalert_ddb"><span span id="remind_nb"></span> (工具仅做自动核保范围内的演示)</span><br/><hr/>
		</div> 
        <div class="ui-block-a getguaranteediv"><div  data-role="button" id="getguarantee">计算保险利益</div></div><br/><br/>
    </div>
    <div class="oneblankdiv"></div>
    <div class="blankdiv"></div>
    <div data-role="footer" class="footer">
    	<div class="footertext">中邮保险销售辅助工具V3.0.2</div>
    </div>
</div> <!--End of page ddb_info -->


<!-- page NianBinfo     -->
<div data-role="page" id="nianbguarantee"  class="info">
    <div data-role="content">
		 <div style=" position: absolute;line-height:1em;right:0.5%;top:0.5em;">
		 	<a data-add-back-btn="true" style="font-size:1em;"   data-rel="back" data-icon="back" 
		 	data-role="button" class="ui-btn-right" >返回</a>
		 </div><br/>
		 
        <div><span class="title">产品亮点：</span> 年年好保你好，重疾保障程度高<br/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生存金早早领，复利收益乐陶陶!<br/><br/>
        </div>
        <div><span class="title">基本信息:</span>年年好重疾保障计划;&nbsp;&nbsp;
        	被保险人年龄<span id="showage" class="parameter"></span>岁,&nbsp;&nbsp;
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
			             <td><span id="payperiodmain" class="parameter"></span></td>
			             <td><span id="duration_nb" class="parameter">10年</span></td>
			             <td><span id="mainpremium" class="parameter">9500元</span></td>
			             <td><span id="maincoverage" class="parameter">50492元</span></td>	             
			           </tr>	           
			           <tr>
			           	<td><span class="parameter">中邮附加重大疾病保险</span></td>
			           	<td><span id="payperiodaddtional" class="parameter"></span></td>
			           	<td><span id="duration_nb2" class="parameter">10年</span></td>
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
             <div class="coveragetext"> 在交费期间内，被保险人生存，可领取<span  id="live_year" class="parameter"></span>年，每年可领取生存年金<span class="coverage" id="annuity"></span>元，若选择累积生息，按3.5%累积利率估算保险期满可领取年金及收益<span class="coverage" id="annuityandprofit"></span>元。</div>
            <div class="title">二、合同权益</div>
             <div class="title2">(一)保单红利</div>
            <div class="coveragetext">可对分红选择累积生息，享受高速发展的中邮保险的经营成果。</div>
             <div class="title2">(二)豁免保险费</div>
            <div class="coveragetext">交费期间内，投保人因遭受意外伤害导致身故或全残，且意外伤害时不满65周岁的，则免交以后各期保险费，本合同继续有效。</div>
             <div class="title2">(三)保单借款</div>
            <div class="coveragetext">最高可以借保单现金价值的90%。</div>      <br/>    			
            <div class="alert">本计划书仅供参考，详细内容以保险合同条款为准。</div><hr/>
            <br/><div class="manger">理财经理：<span style="padding-left:6em">服务电话：</span></div><br/><br/><br/>
        </div>
		</div>
		<br/><br/>
		<div data-role="footer" class="footer">
			<div class="footertext">中邮保险销售辅助工具V3.0.2</div>
		</div> 
</div><!-- end of nianB guarantee -->
	<script type="text/javascript">

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
				
				//coverages used in table
								

				var i_duration;
				
				
				switch(i_years)
				{
					case '3':
					i_duration='10年';					               //保险期间
					i_years_echo=3;                                //交费期间
					f_mainpremium=i_premium*0.92;     //divided premium, 3year-92% 8%;5year 95% 5%
				    f_additionalpremium=i_premium*0.08;	
					
					var i_copiesMain=f_mainpremium/1000;
					var i_copiesAdditional=f_additionalpremium/10;
					
					var f_annuity=f_mainpremium*i_years_echo*0.011;                  /*生存年金*/
					var f_annuityandprofit=0;
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3))*Math.pow(1.035,10-3);
					
					f_maincoverage= arr_mainCoverage3y[i_age]*i_copiesMain;				
					if(i_sex==1){ //computer additional coverage according to sex and insurance period
						//男
						f_additionalcoverage=arr_additionalCoverageMale3y[i_age]*i_copiesAdditional;
					}
					else{
						//女
						f_additionalcoverage= arr_additionalCoverageFemale3y[i_age]*i_copiesAdditional;
					}
					break;
					
					case '3a':
					i_duration='15年';	
					i_years_echo=3;
					f_mainpremium=i_premium*1;     //主险
				    f_additionalpremium=i_premium_add*1;     //附加险
					
					var f_annuity=f_mainpremium*i_years_echo*0.011;                  /*生存年金*/
					var f_annuityandprofit=0;
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3))*Math.pow(1.035,15-3);
					
					var i_copiesMain=f_mainpremium/1000;
					var i_copiesAdditional=f_additionalpremium/10;
					f_maincoverage= arr_mainCoverage3y_a[i_age]*i_copiesMain;				
					if(i_sex==1){ //computer additional coverage according to sex and insurance period
						//男
						f_additionalcoverage=arr_additionalCoverageMale3y_a[i_age]*i_copiesAdditional;
					}
					else{
						//女
						f_additionalcoverage= arr_additionalCoverageFemale3y_a[i_age]*i_copiesAdditional;
					}
					break;
					
					case '3b':
					i_duration='20年';	
					i_years_echo=3;
					f_mainpremium=i_premium*1;     //主险
				    f_additionalpremium=i_premium_add*1;     //附加险
					
					var f_annuity=f_mainpremium*i_years_echo*0.011;                  /*生存年金*/
					var f_annuityandprofit=0;
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3))*Math.pow(1.035,20-3);
					
					var i_copiesMain=f_mainpremium/1000;
					var i_copiesAdditional=f_additionalpremium/10;
					f_maincoverage= arr_mainCoverage3y_b[i_age]*i_copiesMain;				
					if(i_sex==1){ //computer additional coverage according to sex and insurance period
						//男
						f_additionalcoverage=arr_additionalCoverageMale3y_b[i_age]*i_copiesAdditional;
					}
					else{
						//女
						f_additionalcoverage= arr_additionalCoverageFemale3y_b[i_age]*i_copiesAdditional;
					}
					
					break;
					
					case '3c':
					i_duration='至70岁';	
					i_years_echo=3;
					f_mainpremium=i_premium*1;     //主险
				    f_additionalpremium=i_premium_add*1;     //附加险
					
					var f_annuity=f_mainpremium*i_years_echo*0.011;                  /*生存年金*/
					var f_annuityandprofit=0;
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3))*Math.pow(1.035,70-i_age*1-3);
					
					var i_copiesMain=f_mainpremium/1000;
					var i_copiesAdditional=f_additionalpremium/10;
					f_maincoverage= arr_mainCoverage3y_c[i_age]*i_copiesMain;				
					if(i_sex==1){ //computer additional coverage according to sex and insurance period
						//男
						f_additionalcoverage=arr_additionalCoverageMale3y_c[i_age]*i_copiesAdditional;
					}
					else{
						//女
						f_additionalcoverage= arr_additionalCoverageFemale3y_c[i_age]*i_copiesAdditional;
					}
					break;
					
					case '5':
					i_duration='10年';	
					i_years_echo=5;
					f_mainpremium=i_premium*0.95;     //divided premium, 3year-92% 8%;5year 95% 5%
				    f_additionalpremium=i_premium*0.05;     //附加险
					
					var f_annuity=f_mainpremium*i_years_echo*0.011;                  /*生存年金*/
					var f_annuityandprofit=0;
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3)
													+Math.pow(1.035,4)+Math.pow(1.035,5))*Math.pow(1.035,10-5);
					
					
					var i_copiesMain=f_mainpremium/1000;
					var i_copiesAdditional=f_additionalpremium/10;
					f_maincoverage= arr_mainCoverage5y[i_age]*i_copiesMain;				
					if(i_sex==1){ //computer additional coverage according to sex and insurance period
						//男
						f_additionalcoverage=arr_additionalCoverageMale5y[i_age]*i_copiesAdditional;
					}
					else{
						//女
						f_additionalcoverage= arr_additionalCoverageFemale5y[i_age]*i_copiesAdditional;
					}
					break;
					
				
					case '5a':
					i_duration='15年';	
					i_years_echo=5;
					f_mainpremium=i_premium*1;     //主险
				    f_additionalpremium=i_premium_add*1;     //附加险
					
					var f_annuity=f_mainpremium*i_years_echo*0.011;                  /*生存年金*/
					var f_annuityandprofit=0;
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3)
													+Math.pow(1.035,4)+Math.pow(1.035,5))*Math.pow(1.035,15-5);
					
					var i_copiesMain=f_mainpremium/1000;
					var i_copiesAdditional=f_additionalpremium/10;
					f_maincoverage= arr_mainCoverage5y_a[i_age]*i_copiesMain;				
					if(i_sex==1){ //computer additional coverage according to sex and insurance period
						//男
						f_additionalcoverage=arr_additionalCoverageMale5y_a[i_age]*i_copiesAdditional;
					}
					else{
						//女
						f_additionalcoverage= arr_additionalCoverageFemale5y_a[i_age]*i_copiesAdditional;
					}
					break;
					
					case '5b':
					i_duration='20年';	
					i_years_echo=5;
					f_mainpremium=i_premium*1;     //主险
				    f_additionalpremium=i_premium_add*1;     //附加险
					
					var f_annuity=f_mainpremium*i_years_echo*0.011;                  /*生存年金*/
					var f_annuityandprofit=0;
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3)
													+Math.pow(1.035,4)+Math.pow(1.035,5))*Math.pow(1.035,20-5);
					
					var i_copiesMain=f_mainpremium/1000;
					var i_copiesAdditional=f_additionalpremium/10;
					f_maincoverage= arr_mainCoverage5y_b[i_age]*i_copiesMain;				
					if(i_sex==1){ //computer additional coverage according to sex and insurance period
						//男
						f_additionalcoverage=arr_additionalCoverageMale5y_b[i_age]*i_copiesAdditional;
					}
					else{
						//女
						f_additionalcoverage= arr_additionalCoverageFemale5y_b[i_age]*i_copiesAdditional;
					}
					break;
					
					case '5c':
					i_duration='至70岁';	
					i_years_echo=5;
					f_mainpremium=i_premium*1;     //主险
				    f_additionalpremium=i_premium_add*1;     //附加险
					
					var f_annuity=f_mainpremium*i_years_echo*0.011;                  /*生存年金*/
					var f_annuityandprofit=0;
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3)
													+Math.pow(1.035,4)+Math.pow(1.035,5))*Math.pow(1.035,70-i_age*1-5);
					
					var i_copiesMain=f_mainpremium/1000;
					var i_copiesAdditional=f_additionalpremium/10;
					f_maincoverage= arr_mainCoverage5y_c[i_age]*i_copiesMain;				
					if(i_sex==1){ //computer additional coverage according to sex and insurance period
						//男
						f_additionalcoverage=arr_additionalCoverageMale5y_c[i_age]*i_copiesAdditional;
					}
					else{
						//女
						f_additionalcoverage= arr_additionalCoverageFemale5y_c[i_age]*i_copiesAdditional;
					}
					break;
					
				}
				
				$("#payperiodmain").text(i_years_echo+"年");                   //交费期间
				$("#payperiodaddtional").text(i_years_echo+"年");              //交费期间
				
				$("#duration_nb").text(i_duration);                  //回显保险期间
				$("#duration_nb2").text(i_duration);                  //回显保险期间
				
						
				$("#mainpremium").text(formatNum(f_mainpremium));       //回显主险金额
				$("#additionalpremium").text(formatNum(f_additionalpremium));      //回显附加险金额
				
				$("#maincoverage").text(formatNum(f_maincoverage));                               //主险保额
				$("#additionalcoverage").text(formatNum(f_additionalcoverage));                   //附加险保额
				
				$("#totalpremium").text(formatNum((f_mainpremium+f_additionalpremium)*1));       //年交费合计
				
				//coverages used in text 
				$("#seriousillness").text($("#additionalcoverage").text());
				
				var f_gomin=(i_age<18) ? f_mainpremium+f_additionalpremium : f_mainpremium+f_additionalcoverage ;
				$("#gomin").text(formatNum(f_gomin));
				var f_gomax=(i_age+INSURANCEPERIOD > 18) ?  f_mainpremium*i_years_echo+f_additionalcoverage
								 : (f_mainpremium+f_additionalpremium)*i_years_echo;
				$("#gomax").text(formatNum(f_gomax));
				
				$("#unexpectedgomin").text($("#gomin").text());
				$("#unexpectedgomax").text($("#gomax").text());
				
				$("#termination").text($("#maincoverage").text());
				
				
				/*20150731 hj upd begin*//*
				var f_annuity=f_mainpremium*i_years_echo*0.011;
				//count annuity and it's profit,interest rate use 3.5% 1+3.5%=1.035
				$("#annuity").text(formatNum(f_annuity));
				var f_annuityandprofit=0;
				if(i_years_echo==3){
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3))*Math.pow(1.035,10-3);
				}
				else{
					f_annuityandprofit=f_annuity*(Math.pow(1.035,11/12)+Math.pow(1.035,2)+Math.pow(1.035,3)
													+Math.pow(1.035,4)+Math.pow(1.035,5))*Math.pow(1.035,10-5);
				}
				*/
				
				
				$("#annuity").text(formatNum(f_annuity));
				$("#annuityandprofit").text(formatNum(f_annuityandprofit));
				$("#live_year").text(i_years_echo);
				
			}		/*年B部分暂时注销测试*/			

			
		 
		$(document).on("pageinit", function() {		

		/*年年好重疾  info  */ 
			  $("#threeYear").click(function(){	
					$("#explain_nb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age').attr('max','55');
					$('#age').val(35).change();
			}); 
			$("#threeYear_a").click(function(){	
					$("#explain_nb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age').attr('max','55');
					$('#age').val(35).change();
			}); 
			$("#threeYear_b").click(function(){	
					$("#explain_nb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age').attr('max','50');
					$('#age').val(35).change();
			}); 
			$("#threeYear_a").click(function(){	
					$("#explain_nb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age').attr('max','55');
					$('#age').val(35).change();
			}); 
			$("#fiveYear").click(function(){	
					$("#explain_nb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age').attr('max','55');
					$('#age').val(35).change();
			});  
			$("#fiveYear_a").click(function(){	
					$("#explain_nb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age').attr('max','55');
					$('#age').val(35).change();
			});  
			$("#fiveYear_b").click(function(){	
					$("#explain_nb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age').attr('max','50');
					$('#age').val(35).change();
			});  
			$("#fiveYear_c").click(function(){	
					$("#explain_nb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age').attr('max','55');
					$('#age').val(35).change();
			});
		
			$("#getguarantee").click(function(){
			//alert("B");
				//if premiuOK,renew & show page nianbguarantee
				i_money_check=$("#premium_input_nb").val();                    /*强行赋值,检查函数使用*/;				
				i_money_check_nb=$("#premium_input_nb2").val();                    //强行赋值,检查函数使用
				i_years=$('input:radio[name="years_nb"]:checked').val();
				if(premiumOK()&&premiumOK_nb()){
					i_age=$("#age").val();
					i_sex=$("#male").is(':checked') ? 1:0;
					i_years=$('input:radio[name="years_nb"]:checked').val();
					i_premium=$("#premium_input_nb").val(); //old var  is customerinfo[3]
					i_premium_add=$("#premium_input_nb2").val(); //old var  is customerinfo[3]
					
					computeGuarantee();
					$.mobile.changePage("#nianbguarantee");					
				}
			});	
		});  
	</script>
