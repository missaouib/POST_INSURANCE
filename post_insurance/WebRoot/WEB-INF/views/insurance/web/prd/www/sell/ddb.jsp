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
	<style>
		table .guarantee-list{
			width:98%;
		}
		.guarantee-list thead th,
		.guarantee-list tbody tr:last-child {
			border-bottom: 1px solid #d6d6d6; /* non-RGBA fallback */
			border-bottom: 1px solid rgba(0,0,0,.1);
		}
		.guarantee-list tbody th,
		.guarantee-list tbody td {
			border-bottom: 1px solid #e6e6e6; /* non-RGBA fallback  */
			border-bottom: 1px solid rgba(0,0,0,.05);
		}
		.guarantee-list tbody tr:last-child th,
		.guarantee-list tbody tr:last-child td {
			border-bottom: 0;
		}
		.guarantee-list tbody tr:nth-child(odd) td,
		.guarantee-list tbody tr:nth-child(odd) th {
			background-color: #eeeeee; /* non-RGBA fallback  */
			background-color: rgba(0,0,0,.04);
		}
    </style>
	
    
</head>
<body>
    
<!-- page ddb_info   -->
<div data-role="page"  id="nian_bbb_info" class="info">
    <div data-role="content">
        <div class="customerinfo">
              <div class="customertext">交费及保障期：</div>
              <div class="ui-field-contain">
				<fieldset data-role="controlgroup" data-type="horizontal">	
                          <input type="radio" name="years_ddb" id="threeYear_ddb" value="3" />
                          <label for="threeYear_ddb" data-mini="true">交3年保10年</label>
                          
                          <input type="radio" name="years_ddb" id="fiveYear_ddb_a" value="5a"  checked="checked" />
                          <label for="fiveYear_ddb_a" data-mini="true">交5年保10年</label>
                          
                          <input type="radio" name="years_ddb" id="fiveYear_ddb_b" value="5b"  />
                          <label for="fiveYear_ddb_b" data-mini="true">交5年保15年</label>
                          
                          <input type="radio" name="years_ddb" id="fiveYear_ddb_c" value="5c" />
                          <label for="fiveYear_ddb_c" data-mini="true"> 交5年保20年</label>           
              	</fieldset>                    	
              </div>	
              <hr/><br/>
				<span  class="divage_bbb_40"><div class="customertext" id="explain_bbb" >被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：</div> 
				<input id="age_ddb" type="range"  value="35" min="0" max="60" /></span><br/><hr/><br/>
                 <div class="customertext">主险年交费合计（要求为1000的整数倍，1份起售，单位：元）：</div>
                 <input class="premium_input" id="premium_input_ddb" type="number" data-mini="true" value="1000" min="1000" step="1000"/>
                 <span class="premiumalert">提示:本款保险产品每份保费1000元,最低1份起售,<span span id="remind_bbb"></span>大于1000且为1000整倍数的数值!(工具仅做自动核保范围内的演示)</span><br/><hr/>
				<div class="customertext">附加险年交费合计（每1000元保险金额对应10元保险费，要求输入为10的整数倍，单位：元）：</div>
                 <input class="premium_input1" id="premium_input_ddb2" type="number" data-mini="true" value="0" min="0" step="10"/>
                 <span class="premiumalert_ddb">提示:累计最高保险金额为20000元,且最高保险金额不得超过主险首期保险费(工具仅做自动核保范围内的演示)</span><br/><hr/>
				<!--20150729del <span class="premiumalert_ddb">提示:累计最高保险金额为20000元,且最高保险金额不得超过主险首期保险费的50% (工具仅做自动核保范围内的演示)</span><br/><hr/>  -->
		</div> 
        <div class="ui-block-a getguaranteediv"><div  data-role="button" id="getguarantee_bbb">计算保险利益</div></div><br/><br/>
    </div>
    <div class="oneblankdiv"></div>
    <div class="blankdiv"></div>
    <div data-role="footer" class="footer">
    	<div class="footertext">中邮保险销售辅助工具V3.0.2</div>
    </div>
</div> <!--End of page ddb_info -->

<!---ddb guarantee-->
<div data-role="page" id="bbb_guarantee"  class="info">
    <div data-role="content" class='ddbGrid' >
	    <div style=" position: absolute;line-height:1em;right:0.5%;top:0.5em;">
		 	<a   data-add-back-btn="true" style="font-size:1em;"   data-rel="back" data-icon="back" 
		 		data-role="button" class="ui-btn-right" >返回</a>
		 </div>
    	<div class="ui-block-a">
	        <div class="ui-body ui-body-d">
	            <div><span class="title">产品亮点：</span><span  class="title">1</span>份惊喜  &nbsp;&nbsp;   
		        	<span  class="title">2</span>种选择  &nbsp;&nbsp;   <span  class="title">3</span>项收益 &nbsp;&nbsp;  
		        	<span  class="title">4</span>重保障
		        </div>
		        
	        </div>
	    </div>
	    <div class="ui-block-b">
	        <div class="ui-body ui-body-d">
	            <div><span class="title">基本信息</span>（被保险人年龄<span id="showage_ddb" class="parameter"> </span>岁）<span class="title">:</span> </div>
            	<table data-role="table" data-mode="reflow" class="guarantee-list ui-responsive">
	              <thead>
	                <tr class="ui-bar-d">		                  
	                  <th style="width:35%">保险名称</th>
	                  <th data-priority="2">交费期间</th>
	                  <th data-priority="3">保险期间</th>
	                  <th data-priority="3">年交保费</th>
	                  <th data-priority="4">基本保额</th>
	                </tr>
	              </thead>
	              <tbody>	                
	                <tr>	             
			             <td><span class="parameter">多多保</span></td>
			             <td><span id="payperiodmain" class="parameter"> </span></td>
			             <td><span id="duration_ddb" class="parameter"> </span></td>
			             <td><span id="mainpremium" class="parameter"> </span></td>
			             <td><span id="maincoverage" class="parameter"> </span></td>	             
			           </tr>	           
			           <tr>
			           	<td><span class="parameter">附加意外伤害医疗</span></td>
			           	<td><span id="payperiodaddtional" class="parameter"> </span></td>
			           	<td><span id="duration_ddb2" class="parameter"> </span></td>
			           	<td><span id="additionalpremium" class="parameter"> </span></td>
			           	<td><span id="additionalcoverage" class="parameter"> </span></td>
			           </tr>
			           <tr>
			           	<td><span class="parameter">年交费合计：</span></td>
			           	<td>&nbsp;</td>
			           	<td>&nbsp;</td>
			           	<td><span id="totalpremium" class="parameter"></span></td>
			           	<td>&nbsp;</td>
			        </tr>           
	              </tbody>
	            </table>		    				    	
	        </div>
	    </div>
	    <div class="ui-block-c .echo_bbb_a">
	        <div class="ui-body ui-body-d">
	           <div class="title">保险责任：</div>
	           <table data-role="table" data-mode="reflow" class="guarantee-list ui-responsive">
	              <thead>
	                <tr class="ui-bar-d">		                  
	                  <th data-priority="2">保障类别</th>
	                  <th data-priority="3">保障项目</th>	
	                  <th data-priority="2">保障说明</th>
	                  <th data-priority="3">保障额度（元）</th>
	                </tr>
	              </thead>
	              <tbody>	                
	                   <tr>	             
			             <td><span class="parameter">主险</span></td>
			             <td>满期保险金</td>
			             <td>基本保险金额</td>			             
			             <td><span class="coverage" id="f_all_coverage_ddb"></span></td>	             
			           </tr>	           
			           <tr>	             
			             <td><span class="parameter">主险</span></td>
			             <td>积累生存年金</td>
			             <td>自第三个保单周年日起，按首期保险费的既定比例给付生存年金</td>			             
			             <td><span class="coverage" id="annuity_ddb"></span></td>	             
			           </tr>	           
			           <tr>	             
			             <td><span class="parameter">主险</span></td>
			             <td>累计红利（高）</td>
			             <td>专业投资理财带来收益</td>			             
			             <td><span class="coverage" id="ljhl_g">1495</span></td>	             
			           </tr>	
					   <tr>	             
			             <td><span class="parameter">主险</span></td>
			             <td>累计红利（中）</td>
			             <td>专业投资理财带来收益</td>			             
			             <td><span class="coverage" id="ljhl_z">854.3</span></td>	             
			           </tr>	
			           <tr>	             
			             <td><span class="parameter">主险</span></td>
			             <td>累计红利（低）</td>
			             <td>专业投资理财带来收益</td>			             
			             <td><span class="coverage" id="ljhl_d">213.6</span></td>	             
			           </tr>	    
			           <tr>	             
			             <td><span class="parameter">主险</span></td>
			             <td>身故、全残保险金</td>
			             <td>已交保费的105%</td>			             
			             <td>
			             	<span class="coverage age_ddb_min"></span>
			             	<span class="coverage">~</span>
			             	<span class="coverage age_ddb_max"></span>
			             </td>	             
			           </tr>	           
			           <tr>	             
			             <td><span class="parameter">主险</span></td>
			             <td>一般意外身故、全残保险金</td>
			             <td>
			             	<span class='biggerthan18'>基本保险金额的<span  class="parameter">2倍</span>给付</span> 
			             	<span class='lessthan18'>已交保费的105%</span>			             	
			             </td>			             
			             <td>
			             	<span class="coverage biggerthan18" id="unexpected_ddb"></span>
			             	<span class='lessthan18'>
								<span class="coverage age_ddb_min"></span>
				             	<span class="coverage">~</span>
				             	<span class="coverage age_ddb_max"></span>
							</span>	
			             </td>	             
			           </tr>	           
			           <tr>	             
			             <td><span class="parameter">主险</span></td>
			             <td>自驾车意外身故、全残保险金</td>
			             <td>
				             <span class='biggerthan18'>基本保险金额的<span  class="parameter">5倍</span>给付</span> 
				             <span class='lessthan18'>已交保费的105%</span>			             	
			             </td>			             
			             <td>
			             	<span class="coverage biggerthan18" id="unexpected_car_ddb"></span>
			             	<span class='lessthan18'>
								<span class="coverage age_ddb_min"></span>
				             	<span class="coverage">~</span>
				             	<span class="coverage age_ddb_max"></span>
							</span>	
			             </td>	             
			           </tr>	           
			           <tr>	             
			             <td><span class="parameter">主险</span></td>
			             <td>公共交通意外身故、全残保险金</td>
			             <td>
			             	<span class='biggerthan18'>基本保险金额的<span  class="parameter">5倍</span>给付</span> 
			             	<span class='lessthan18'>已交保费的105%</span>			             	
			             </td>			             
			             <td>
			             	<span class="coverage  biggerthan18" id="unexpected_bus_ddb"></span>
			             	<span class='lessthan18'>
								<span class="coverage age_ddb_min"></span>
				             	<span class="coverage">~</span>
				             	<span class="coverage age_ddb_max"></span>
							</span>	
			             </td>	             
			           </tr>	
			           <tr>	             
			             <td><span class="parameter">附加险</span></td>
			             <td>意外伤害医疗保险金</td>
			             <td>按条款给付保险责任期间遭受意外伤害而发生的合理且必要的医疗费用，</td>			             
			             <td><span class="coverage" id="aditionalRiskGuarante_ddb"></td>	             
			           </tr>	           
	              </tbody>
	            </table>	
	            <div class="alert include18" >备注：保险事故发生时，被保险人未满18周岁，主险责任中满期保险金、
	            		积累生存年金、累计红利同上表，疾病身故保障、一般意外身故保障、自驾车意外保障、公共交通意外保障
	            		均为已交保费的105%。
	            </div>
	        </div>
	    </div>
	    <div class="ui-block-d">
	        <div class="ui-body ui-body-d">
	            <span class="title">合同权益：</span><span>保单借款</span>
             	<span class="coveragetext">，最高可以借保单现金价值的90%。</span> 
	        </div>
	    </div>	    
	    
	    <div class="alert">本计划书具体金额仅供参考，详细内容以保险合同条款为准。</div><br/><hr/><br/>
	    <div class="manger">理财经理：<span style="padding-left:6em">服务电话：</span></div>
   	</div>	<!-- End of data-role=content -->
				
		<br/><br/>
		<div data-role="footer" class="footer">
			<div class="footertext">中邮保险销售辅助工具V3.0.2</div>
		</div>	 
</div><!-- end of nian_bbb guarantee -->

<script type="text/javascript">			
			function computeGuarantee_bbb(){
				//$("#customerinfoshow").text("success:"+customerinfo.join());
				//alert($("#payperiodmain").text());
				
				var i_duration=0;
				
				$("#showage_ddb").text(i_age);
				
				if(i_years==3){
					i_years_ddb=3;
				}
				else
				{
					i_years_ddb=5;
				}

				$("#payperiodmain").text(i_years_ddb+"年");
				$("#payperiodaddtional").text(i_years_ddb+"年");
				//coverages used in table
				
				f_mainpremium=i_premium*1;//divided premium, 3year-92% 8%;5year 95% 5%
				f_additionalpremium=i_premium_add*1;				
				$("#mainpremium").text(formatNum(f_mainpremium));              //保费
				$("#additionalpremium").text(formatNum(f_additionalpremium));   //附加险保费
				
				var i_copiesMain=f_mainpremium/1000;
				var i_copiesAdditional=f_additionalpremium/10;
				
				 
				 if(i_years=="3"){
					f_maincoverage=arr_ddb_3[i_age]*i_copiesMain;
					i_duration=10;					               //保险期间
				}
				else
				{
					if(i_years=="5a"){
						f_maincoverage=arr_ddb_5a[i_age]*i_copiesMain;
						i_duration=10;					               //保险期间
					}
					else
					{
						if(i_years=="5b"){
							f_maincoverage=arr_ddb_5b[i_age]*i_copiesMain;
							i_duration=15;					               //保险期间
						}
						else
						{
							f_maincoverage=arr_ddb_5c[i_age]*i_copiesMain;
							i_duration=20;					               //保险期间
						}
					}
				}  
				
				$("#duration_ddb").text(i_duration+"年");                  //回显保险期间
				$("#duration_ddb2").text(i_duration+"年");                  //回显保险期间
				 // f_maincoverage=((i_years==3)? arr_ddb_5b[i_age]: arr_ddb_5c[i_age])*i_copiesMain;	
				 
				$("#maincoverage").text(formatNum(f_maincoverage));          //主险保险金额						
				f_additionalcoverage=i_copiesAdditional*1000;
				$("#additionalcoverage").text(formatNum(f_additionalcoverage));       //回显附加险保险金额
				
				
				$("#totalpremium").text(formatNum((f_mainpremium+f_additionalpremium)*1));
		
				$("#unexpected_car_ddb").text(formatNum((f_maincoverage)*5));      //5倍保险金额
				$("#unexpected_bus_ddb").text(formatNum((f_maincoverage)*5));      //5倍保险金额
				$("#unexpected_ddb").text(formatNum((f_maincoverage)*2));      //2倍保险金额
				
				$("#disease_bbb").text(formatNum((f_mainpremium)*1.05));      //1.05倍保费
				$("#disease_bbb_max").text(formatNum((f_mainpremium)*1.05*i_years_ddb));      //1.05倍保费
				
				$("#annuity_ddb").text(formatNum((f_mainpremium)*i_years_ddb/100));      //生存金
				
				$("#f_all_coverage_ddb").text(formatNum(f_maincoverage));          //满期保险金		
				
				$(".age_ddb_min").text(formatNum((f_mainpremium)*1.05));            //1.05倍保费
				$(".age_ddb_max").text(formatNum((f_mainpremium)*1.05*i_years_ddb));      //1.05倍保费*num of pay years
				//if less than 18 years old at the end of the insurance period,show simple guarante
				var endAge=i_duration*1+i_age*1;
				if( endAge < 18 ){
					$('.lessthan18').show();
					$('.biggerthan18').hide();					
					$('.include18').hide();
				}else{
					$('.lessthan18').hide();
					$('.biggerthan18').show();					
					if(i_age*1 < 18){
						$('.include18').show();
					} else{ $('.include18').hide(); }
				}
								
				//ljhl_g 1495,ljhl_z 854.3,ljhl_d 213.6/1000元
				$("#ljhl_g").text(formatNum((f_mainpremium)*1.495));
				$("#ljhl_z").text(formatNum((f_mainpremium)*0.8543));
				$("#ljhl_d").text(formatNum((f_mainpremium)*0.2136));
				
				$("#aditionalRiskGuarante_ddb").text(formatNum(f_additionalpremium*100));
				$(".echo_bbb_a").show();
			}			

		$(document).on("pageinit", function() {	

			/*年年好百倍保  info  */ 
			  $("#threeYear_ddb").click(function(){	
					$("#explain_bbb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age_ddb').attr('max','60');
					$('#age_ddb').val(35).change();
			}); 
			$("#fiveYear_ddb_a").click(function(){	
					$("#explain_bbb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age_ddb').attr('max','60');
					$('#age_ddb').val(35).change();
			}); 
			$("#fiveYear_ddb_b").click(function(){	
					$("#explain_bbb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age_ddb').attr('max','55');
					$('#age_ddb').val(35).change();
			}); 
			$("#fiveYear_ddb_c").click(function(){	
					$("#explain_bbb").text("被保险人年龄（出生满30天-保险合同到期时年龄不超过70岁）：");   
					$('#age_ddb').attr('max','50');
					$('#age_ddb').val(35).change();
			});
		
			$("#getguarantee_bbb").click(function(){
				//if premiuOK,renew & show page nianbguarantee
				 i_money_check=$("#premium_input_ddb").val();                    //强行赋值,检查函数使用 
				 i_money_check_ddb=$("#premium_input_ddb2").val();                    //强行赋值,检查函数使用
				  
				  
				if(premiumOK()&&premiumOK_ddb()){
				
					i_age=$("#age_ddb").val(); //defined in func_all.js,public var 
					//i_sex=$("#male").is(':checked') ? 1:0;
					i_years=$('input:radio[name="years_ddb"]:checked').val();
					i_liability=$('input:radio[name="liability_bbb"]:checked').val();
					i_premium=$("#premium_input_ddb").val(); //old var  is customerinfo[3]
					i_premium_add=$("#premium_input_ddb2").val(); //old var  is customerinfo[3]
				
					computeGuarantee_bbb();
					$.mobile.changePage("#bbb_guarantee");
					
				}
			});
		});  
	</script>
