<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
body,td,th {
	font-size: 16px;
}
input {  
border: expression(this.type=="text"?"1px solid #04576f":"");  
} 
</style>
<link rel="stylesheet" href="css/jquery.mobile-1.3.2.min.css" type="text/css"></link>
    <link href="css/myfont.css" rel="stylesheet" type="text/css">
	<script src="js/jquery-1.11.0.min.js"></script>
	<script src="js/jquery.mobile-1.3.2.min.js"></script>
<script language="javascript" type="text/javascript">
	function mycount() {
		var copies =  $("#copies").val();
		var payTerm = $('input[name="payTerm"]:checked').val();
		var brst;
		var fjrst;
		if(payTerm == 3) {
			brst = copies * 1000 * 920 / 1000;
			fjrst = copies * 1000 * 80 / 1000;
		} else {
			brst = copies * 1000 * 950 / 1000;
			fjrst = copies * 1000 * 50 / 1000;
		}
		$("#brst")[0].value=brst;
		$("#fj")[0].value=fjrst;
		return true;
	}
</script>
</head>
<body>
<ul data-role="listview">
<div data-role="collapsible" style="display:none">
	<h3>填单要求</h3>
		<table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#FF6600">
		  <tr>
		    <td colspan="2" align="center" bgcolor="#FFFFFF">填单要求</td>
	      </tr>
		  <tr>
		    <td colspan="2" bgcolor="#FFFFFF">差错类型：漏填，漏选，涂改，填写错误，与关联信息不符，其他错误</td>
	      </tr>
		  <tr>
		    <td colspan="2" bgcolor="#FFFFFF">为保证新契约承保件的客户信息真实性和完整性，<strong class="my">投保单中的填写项目不得出现漏填、错填及涂改现象，由于投保人原因导致的涂改应指导修改人（即投保人）在修改处旁边亲笔签字确认，不得出现除投保人以外的人员修改投保单中的客户信息</strong>。具体要求如下</td>
	      </tr>
		  <tr>
		    <td bgcolor="#FFFFFF">以下信息必须<strong><span class="myred">正确填写，不得出现差错</span></strong>：</td>
		    <td bgcolor="#FFFFFF"><strong class="myred">姓名,职业,出生日期、证件号码、国籍、证件有效期至、受益人信息,险种名称、投保份数、交费期间、保险期间、保险费、小额贷款保险金额、投保告知、投保人抄录项、投保单签名日期、提示书签名日期、回执签名日期、代理机构签章</strong></td>
	      </tr>
		  <tr>
		    <td bgcolor="#FFFFFF">为保证整单有效性，以下信息只可以修改两处：</td>
		    <td bgcolor="#FFFFFF" class="my"><strong>职业代码、电话/手机、联系地址、邮编、保险费合计、交费账户信息、受益顺序、受益比例、被保险人与投保人关系、受益人与被保险人关系、贷款机构、借款合同编号、借款金额、借款期间</strong></td>
	      </tr>
		  <tr>
		    <td bgcolor="#FFFFFF">为保证整单有效性，以下信息只可修改三处：</td>
		    <td bgcolor="#FFFFFF">性别，有效证件名称，婚姻状况，基本保额，交费方式，生存保险金领取方式，红利领取方式，保险费缴纳方式，保费自动垫交，争议处理方式，备注/特约，代理机构其他信息</td>
	      </tr>
  </table>
</div>
<div data-role="collapsible">
  <h3>填单要点</h3>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#FF3300">
  <tr>
    <td width="19%" bgcolor="#FFFFFF">险种名称</td>
    <td width="81%" colspan="2" bgcolor="#FFFFFF">中邮富富余多多保两全保险（分红型）。<br>
      可简写为：<strong>多多保</strong></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">投保单</td>
    <td colspan="2" bgcolor="#FFFFFF">必须使用<strong>2016版</strong>投保单。</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">复印件</td>
    <td colspan="2" bgcolor="#FFFFFF">留存客户身份证复印件，卡折复印件，如大额件需被保险人非投保人本人需要关系证明，如指定受益人，也需关系证明。（反洗钱要求，与省分行稽核要求一致）</td>
  </tr>
  <tr>
    <td rowspan="10" bgcolor="#FFFFFF">填单要点</td>
    <td colspan="2" bgcolor="#FFFFFF" class="myred">注：60周岁以上客户作为投保人需要走人工核保，邮保通限制承保出单。<br>
      <strong>广东：60周岁或以上必须进行录音录像且转人工核保。</strong></td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#FFFFFF">险种名称<br>
      主险名称：<span class="my"><strong>中邮富富余多多保两全保险（分红型）</strong></span><br>
      （可<strong>简写</strong>为：<strong>多多保</strong>）<br>
      附件险名称：<strong class="myred">中邮附加意外伤害医疗保险</strong>（可选购买）</td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#FFFFFF"><strong>职业请填写最高类别的职业</strong>,如建筑水电工（室内），职业代码为：0701015<br>
        <strong>主险承保职业</strong>：1-6类；<br>
        附加险职业：1-4类</td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#FFFFFF" class="my">份数：1份起售 每份1000元</td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#FFFFFF">交费期间：3年交（保10年）、5年交 （保10年、15年、20年）</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">保费比例</td>
    <td bgcolor="#FFFFFF">附加险保险金额最低1000元，不超过主险首期保费且是1000元的整数，最高20000保险金额。<strong>每1000元基本保险金额保费10元</strong>。</td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#FFFFFF">告知事项在投保单背面，由投保人阅读告知事项并<strong>亲自</strong>在告知栏勾选实际情况</td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#FFFFFF"><strong>投保人抄录语句项必须由投保人亲笔抄写</strong></td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#FFFFFF">投保人与被保险人分别需要<strong>本人亲笔签名</strong>，不得由他人代签名</td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#FFFFFF">如被保险人为<span class="myred"><strong>未成年人</strong></span>（未满18岁），被保险人签名处由其<strong><span class="myred">监护人</span></strong>（如父母）<strong><span class="myred">亲笔签名</span></strong>（监护人姓名）</td>
  </tr>
</table>
</div>
<div data-role="collapsible">
            <h3>给客户的承保资料 	&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-li-count">6项</span></h3>
            <p>
                <li>共七项：<br>
                  <strong class="my"><span class="myred">1、保险单；<br>
                  2、投保单（客户联）（2016版投保单）；<br>3、投保提示书（投保书附带的那份）；<br>
                  4、产品说明书+保险条款；<br>5、收费凭证；<br>
                  6、附加险产品说明书及条款（可选）；<br>
                  7、银行自动转账授权书（客户联）。</span></strong> <br>以上资料放保单夹中连同保单夹一同给客户。</li>
            </p>
        </div>
        <div data-role="collapsible">
            <h3>归档资料	&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-li-count">4项</span></h3>
            <p>
                <li>共6项：<br><strong class="my"><span class="myred">1、投保单（公司联）（2016版投保单）；<br>2、投保提示书；<br>
                3、中邮人寿保险股份有限公司银行保险投保书补充声明（单联）（如使用2016版投保单不需提供）；<br>4、银行自动转账授权书（公司联）；<br>
                5、合同回执（如使用2016版投保单不需提供）；<br>
                  6、身份证复印件、卡折复印件（每单留存），或相关关系证明。<br><br>特殊情况下：<br></span></strong></li>
                  1、<strong class="my">邮保通出单承保提示书</strong>（适用于邮保通出单无手机号码客户，可由网点人员填写）；<br>
2、<span class="my"><strong>核心系统出单承保提示书</strong></span>（适用于转人核件无手机号码的客户，可由网点人员填写）<br>
3、<span class="my"><strong>投保单填写授权委托书</strong></span>（适用于填写有困难或者文化程度低的客户，在客户签名授权后，可由受托人员代为填写投保单）
            </p>
        </div>
</body>
</html>