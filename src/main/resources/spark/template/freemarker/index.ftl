<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>?!@# FREEMARKER TEST #$@!</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <style>
        <#include "base.css">
    </style>
</head>
<body>
<#--Header-->
<header>
    <div>
        <h1>?!@# FREEMARKER TEST #$@!</h1>
    </div>
</header>
<hr>
<fieldset class="ft_fieldset">
    <form action="/template" method="get">
        <label for="templates">Choose a template:</label>
        <select id="templates" name="id">
            <#list template_files as file>
                <option value="${file.getName()}">${file.getName()}</option>
            </#list>
        </select>
        <br/>
        <button class="ft_button" type="submit">Let's see it!</button>
    </form>
</fieldset>
</body>
</html>