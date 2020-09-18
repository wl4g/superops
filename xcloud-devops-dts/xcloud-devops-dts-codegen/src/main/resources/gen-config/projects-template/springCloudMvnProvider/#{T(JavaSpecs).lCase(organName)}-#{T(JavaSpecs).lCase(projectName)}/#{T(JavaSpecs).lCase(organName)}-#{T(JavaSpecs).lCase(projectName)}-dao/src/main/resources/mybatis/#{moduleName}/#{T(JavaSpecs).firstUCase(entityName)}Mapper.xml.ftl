<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.dao.${moduleName}.${entityName?cap_first}Dao">
    <resultMap id="BaseResultMap" type="${packageName}.commons.bean.${moduleName}.${entityName?cap_first}">
        <#list genTableColumns as param>
            <#if param.isPk == 1>
                <id column="${param.columnName}" jdbcType="${param.columnType}" property="${param.attrName}" />
            <#else>
                <result column="${param.columnName}" jdbcType="${param.columnType}" property="${param.attrName}" />
            </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        <#list genTableColumns as param>
            ${param.columnName}<#if param_has_next>,</#if></#list>
    </sql>

    <select id="selectByPrimaryKey" parameterType="java.lang.Integer" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from ${tableName}
        where ${pk.columnName} = ${r'#{'}${pk.attrName},jdbcType=${pk.columnType}}
    </select>

    <delete id="deleteByPrimaryKey" parameterType="java.lang.Integer">
    delete from ${tableName}
    where ${pk.columnName} = ${r'#{'}${pk.attrName},jdbcType=${pk.columnType}}
    </delete>

    <insert id="insertSelective" parameterType="${packageName}.commons.bean.${moduleName}.${entityName?cap_first}">
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list genTableColumns as param>
               <if test="${param.attrName} != null">
                   ${param.columnName},
               </if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list genTableColumns as param>
               <if test="${param.attrName} != null">
                   ${r'#{'}${param.attrName},jdbcType=${param.columnType}},
               </if>
            </#list>
        </trim>
    </insert>

    <update id="updateByPrimaryKeySelective" parameterType="${packageName}.commons.bean.${moduleName}.${entityName?cap_first}">
        update ${tableName}
        <set>
        <#list genTableColumns as param>
            <if test="${param.attrName} != null">
                ${param.columnName} = ${r'#{'}${param.attrName},jdbcType=${param.columnType}},
            </if>
        </#list>
        </set>
        where id = ${r'#{'}id,jdbcType=INTEGER}
    </update>


    <select id="list" resultMap="BaseResultMap" parameterType="java.util.Map" >
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        where del_flag!=1
        <if test="name != null and name != ''" >
            AND `name` LIKE CONCAT('%','${name}','%')
        </if>
    </select>

</mapper>