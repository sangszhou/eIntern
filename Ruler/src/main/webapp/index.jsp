<!DOCTYPE html>
<!-- saved from url=(0050)http://getbootstrap.com/examples/starter-template/ -->
<html lang="en">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/auction-big.png">
	
    <title>Ruler</title>

	
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/jquery-ui.css" rel="stylesheet">
    <!-- Custom styles for this template -->
	
	<!-- My import -->
	<link href="css/jquery.dataTables.css" rel="stylesheet">
	<link href="css/main.css" rel="stylesheet">
	<script type="text/javascript" src="js/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="js/typeahead.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<!-- My import end -->
	
  <style>[touch-action="none"]
  { -ms-touch-action: none; touch-action: none; }
  [touch-action="pan-x"]
  { -ms-touch-action: pan-x; touch-action: pan-x; }
  [touch-action="pan-y"]{ -ms-touch-action: pan-y; touch-action: pan-y; }[touch-action="scroll"],[touch-action="pan-x pan-y"],[touch-action="pan-y pan-x"]{ -ms-touch-action: pan-x pan-y; touch-action: pan-x pan-y; }</style><style id="style-1-cropbar-clipper">/* Copyright 2014 Evernote Corporation. All rights reserved. */
.en-markup-crop-options {
    top: 18px !important;
    left: 50% !important;
    margin-left: -90px !important;
    width: 180px !important;
    border: 2px rgba(255,255,255,.38) solid !important;
    border-radius: 4px !important;
}

.en-markup-crop-options div div:first-of-type {
    margin-left: 0px !important;
}
</style>



</head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" >Ruler</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a >Home</a></li>
            <li><a href="https://github.corp.ebay.com/DevExTech/maven-time-tracking/blob/master/core/src/main/resources/ride-filters.xml">Filter XML</a></li>
            
          </ul>
          
          <ul class="nav pull-right">
          
          <li class="divider-vertical"></li>
          <li class="dropdown">
            <a id = "alogin" class="dropdown-toggle"  data-toggle="dropdown">Log In <strong class="caret"></strong></a>
            <div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
                <form id="fmlogin" action="http://10.249.65.27:8080/Ruler/VerifyUser" method="post"> 
                    Username:<br /> 
                    <input id="iptusername" type="text" name="username" value="" /> 
                    <br />
                    Password:<br /> 
                    <input id="iptpassword" type="password" name="password" value="" /> 
                    <br />
                    <input id = "btnlogin" type="submit" class="btn btn-info" value="Login"/> 
                </form> 
            </div>
          </li>
        </ul>
          
        </div><!--/.nav-collapse -->

        
        
      </div>
    </div>

    <div>
    <!-- Left column starts -->
	  <div class="left-column">
	  	<div class="table" class="table table-striped table-bordered" style="height: 32em">
	  		<table id="table" class="display" >
		        <thead>
		            <tr>
		                <th>Access Time</th>
		                <th>IDE Type</th>
		                <th>IDE Version</th>
		
		            </tr>
		        </thead>		 				 				 		
		    </table>
	  	</div>
	  		
		    <br>
		    <!-- Log begins -->
	  	<div id="log" class="log">
	  		
	  	</div>
	  <!-- log ends -->
	  </div>
	<!-- left column ends -->
	<!-- right column starts -->
	  <div class="right-column">
	  
	  	<div class ="rule">
	  		<div class="panel panel-default">
			   <div class="panel-heading">
			      <h3 class="panel-title">
			         Rule
			      </h3>
			   </div>
			   <div id="divpanelbody" class="panel-body" style="height:32.5em">
			      
			      <form id ="fmkp" class="bs-example bs-example-form" role="form">
				  
				       <div id="divkeyword" class="input-group">
				         <span class="input-group-addon">Keyword</span>
				         <input id="iptkeyword0" type="text" class="form-control">
				         <label id="addkeyword"  class="input-group-addon">+</label>
				      </div>
				      <br>
				
				       <div id="divpattern" class="input-group">
				         <span class="input-group-addon">Pattern</span>
				         <input id="iptpattern0" type="text" class="form-control">				         
				          <label id="addpattern" class="input-group-addon">+</label>
				      </div>
				      
				   </form>
				   <br>
				   <button id= "btnsearch" type="button" class="btn btn-default">Search KP</button>
				   <button id="btntest" type="button" class="btn btn-default">Test KP</button>
				   <button id="btnrestore" type="button" class="btn btn-default">Restore KP</button>
			   	   <!-- <button id="btnaddrule" type="button" class="btn btn-default">Add Rule</button> -->
			   	   
			   	   <br>
			   	   <br>
			   	   			  
			   	    <form autocomplete="on" id="fmrule" class="bs-example bs-example-form" role="form">
				      <div class="input-group">
				         <span class="input-group-addon">Category Name </span>
				         <input id="iptcategoryname" autocomplete="on" type="text" class="form-control" placeholder="Not Null">
				      </div>
				      <br>
				
				      <div class="input-group">
				         <span class="input-group-addon">Filter Name </span>
				         <input id="iptfiltername" type="text" class="form-control" placeholder="Not Null">
				      </div>
				      <br>
				      <div class="input-group">
				         <span class="input-group-addon">Filter Description</span>
				         <input id="iptfilterdescription" type="text" class="form-control" >
				      </div>
				   				      
				   </form>			      
			   </div>
			  		
			</div>
	  </div>
	  <!-- consoles starts -->
				   	<div id="console" class="panel panel-default" style="max-height:22em">
					   <div class="panel-heading">
					      <h3 class="panel-title">
					         Console
					      </h3>
					   </div>
					   <div id="consoleinfo" class="panel-body">
					      
					   </div>
					</div>
				   <!-- consoles ends -->
	  </div>
    	<!-- right column ends -->
    </div><!-- /.container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    

</body>
 <script src="js/main.js"></script>

 
</html>