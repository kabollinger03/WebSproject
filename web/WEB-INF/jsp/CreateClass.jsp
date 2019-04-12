

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Class</title>
    </head>
    <body>
        <s:form commandName="excel">
            <Table>
                <tr>
                    <td> Stream:</td>
                    <td><select name="streamName">
                        <option value="">Select</option>
                        <option value="javaFSD">Java FSD</option>
                        <option value="bigData">Big Data</option>
                        <option value="netFSD">.Net FSD</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td> Location:</td>
                    <td><input type="text" name="location"/></td>
                </tr>
                <tr>
                    <td>Site</td>
                    <td><select name="site">
                        <option value="">Select</option>
                        <option  id="offSite" value="CHI">CHI</option>
                        <option  id="onSite" value="OPA">OPA</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Upload Excel</td>
                    <td><input type="file" name="file">
                </tr>
                <tr>
                    <td><input type="submit" value="Submit"/></td>
                </tr>
            </Table>
        </s:form>
    </body>
</html>
