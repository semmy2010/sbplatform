SELECT * FROM sb_minidao WHERE 1=1
<#if sbMinidao.userName ?exists && sbMinidao.userName ?length gt 0>
	and user_name = :sbMinidao.userName
</#if>
<#if sbMinidao.mobilePhone ?exists && sbMinidao.mobilePhone ?length gt 0>
	and mobile_phone = :sbMinidao.mobilePhone
</#if>
<#if sbMinidao.officePhone ?exists && sbMinidao.officePhone ?length gt 0>
	and office_phone = :sbMinidao.officePhone
</#if>
<#if sbMinidao.email ?exists && sbMinidao.email ?length gt 0>
	and email = :sbMinidao.email
</#if>
<#if sbMinidao.age ?exists && sbMinidao.age ?length gt 0>
	and age = :sbMinidao.age
</#if>
<#if sbMinidao.salary ?exists && sbMinidao.salary ?length gt 0>
	and salary = :sbMinidao.salary
</#if>
<#if sbMinidao.sex ?exists && sbMinidao.sex ?length gt 0>
	and sex = :sbMinidao.sex
</#if>
<#if sbMinidao.status ?exists && sbMinidao.status ?length gt 0>
	and status = :sbMinidao.status
</#if>
