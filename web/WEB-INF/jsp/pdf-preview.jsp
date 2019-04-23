<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<body class="bg-light">
        
        <div>
            <embed src="${employeeID}.pdf" width="100%" height="900" type="application/pdf">
        </div>
            <!--DEV: check what ID is passed in-->
            <h1>ID of employee: ${employeeID}</h1> 
            
    </body>
</html>