var keywordlen = 1;
var patternlen = 1;
var logID = -1;

var username = "ride";
var password = "ride";


var serverurl = "http://10.249.65.27:8080/Ruler/";

$(document).ready(function() {
	
	//$("#btnaddrule").attr("disabled", true);
	
	$("#fmlogin").show();
	
	$("btnlogin").click(function() {
		alert("login button clicked!");
		
		var tempuser = $("#iptusername").val();
		var temppswd = $("#iptpassword").val();
		
		if(username == tempuser && password == temppswd) {
			$("#btnaddrule").attr("disabled", false);
			
		} else {
			consolePrint("Invalid username or password", "danger");
		}
	});
	
    $('#table').dataTable( {
    	"iDisplayLength" : 8,
    	sDefaultContent: '<div class="expand /">',
        "ajax": "http://10.249.65.27:8080/Ruler/InitTable",
    	"columns": [
    	            { "data": "Access Time" },
    	            { "data": "IDE Type" },
    	            { "data": "IDE Version" },  
    	        ],
    });
    
    
    function reloadTable(data) {
    	var table = $('#table').dataTable();
    	table.fnClearTable();
    	table.fnAddData(data);
    	
    }
    
    function consolePrint(log, status) {
    	var consoleins = $("#consoleinfo");
    	consoleins.html(log);
    	
    	if(status == "success") {
    		$("#console").addClass("panel panel-success");
    		
    	} else {
    		$("#console").addClass("panel panel-danger");
    	}
    	
    	//$("#console").removeClass("panel panel-success");
    	//$("#console").addClass("panel panel-default");
    	setTimeout(function() {
    		if(status == "success") {
        		$("#console").removeClass("panel panel-success");	
        	} else {
        		$("#console").removeClass("panel panel-danger");
        	}
    		$("#console").addClass("panel panel-default");
    	}, 2000);
    	
    	//$("#console").addClass("panel panel-default");
    }
    
    $('#table tbody').on( 'click', 'tr', function () {
    	var loginfo = "[INFO] <br>";
    	
    	var table = $('#table').dataTable();	

    	if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');  
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    	
    	var data = table.fnGetData(this);
    	loginfo = loginfo + data["ID"];
    	logID   = data["ID"];
    	$("#log").html("<pre>" + data["Exception"] +"</pre>");
    	//this.insertBefore("<pre> Hello World</pre>", this.childNode[0]);
//    	$(this).append("<br>");
//    	$(this).append("<pre> Hello World</pre>");
    	
//    	var tr = $(this).closest('tr');
//    	var row = table.row(tr);
//    	if(row.child.isShow()) {
//    		consolePrint("show", "success");
//    	} else {
//    		consolePrint("not show", "success");
//    		
//    	}
    	
    } );
    
    function collectData() {
    	var keywords = new Array();
		var patterns = new Array();
		
		var loginfo = "[INFO] <br>";
		for(var i = 0; i < keywordlen; i ++) {
			if($("#iptkeyword"+i).val() != '') {
				keywords.push($("#iptkeyword"+i).val());
			}
		}
		
		for(var i = 0; i < patternlen; i ++) {
			if($("#iptpattern"+i).val() != '') {
				patterns.push($("#iptpattern"+i).val());
			}
		}
		
	
		var data = {};
		data["categoryName"] = $("#iptcategoryname").val();
		
		data["filterName"] = $("#iptfiltername").val();
		data["filterDescription"] = $("#iptfilterdescription").val();
		data["keywords"] = JSON.stringify(keywords);
		data["patterns"] = JSON.stringify(patterns);	
    
		loginfo = loginfo +( $("#iptcategoryname").val()+"<br>");
		loginfo = loginfo +( $("#iptfiltername").val()+"<br>");
		loginfo = loginfo +( $("#iptfilterdescription").val()+"<br>");
		loginfo = loginfo +( data["keywords"] + "<br>");
		loginfo = loginfo +( data["patterns"] + "<br>");
		// print to console
		//consolePrint(loginfo, "success");
    
		return data;
    }
    
    
    
    
    $("#addPattern").click(function() {
		var _len=$("#searchPattern tr").length;
		$("#searchPattern").append("tr"
			+"<td align=\"left\">Pattern:</td>"
			+ "<td><input type=\"text\" id=\"pattern"+_len+"\"</td>"
			+ "</tr>");
		//alert("id=pattern"+_len);
	});
    

    
    
    $("#addkeyword").click(function() {
    	
    	var index = keywordlen ++;
    	var code = "<div id=\"divkeyword" + index +"\"class=\"input-group\">" +
				         "<span id=\"spankeyword"+index+"\"class=\"input-group-addon\">Keyword</span>"+
				         "<input id=\"iptkeyword"+index+"\" type=\"text\" class=\"form-control\">"+				       
				      "</div>";
    	
    	$("#fmkp").append( "<br>"+code);
    	consolePrint("Add one keyword div. Code is: <br>", "success");
    	//alert(code);
    });
    $("#addpattern").click(function() {
    	
    	var index = patternlen ++;
    	var code = "<div id=\"divpattern"+index+"\"class=\"input-group\">" +
				         "<span id=\"spanpattern"+index+"\" class=\"input-group-addon\">Pattern</span>"+
				         "<input id=\"iptpattern"+index+"\" type=\"text\" class=\"form-control\">"+				       
				      "</div>";
    	
    	$("#fmkp").append("<br>"+ code);
    	//alert(code);
    	consolePrint("Add one pattern div. Code is: <br>", "success");
    });
    
    $("#btnrestore").click(function() {
    	consolePrint("Restore KP form.", "success");
    	$("#fmkp").empty();
    	
    	var code = "<form id =\"fmkp\" class=\"bs-example bs-example-form\" role=\"form\">"+
	       "<div id=\"divkeyword\" class=\"input-group\">" +
	         "<span class=\"input-group-addon\">Keyword</span>"+
	         "<input id=\"iptkeyword0\" type=\"text\" class=\"form-control\">" +
	         "<label id=\"addkeyword\"  class=\"input-group-addon\">+</label>" +
	      "</div>" +
	      "<br>" +
	
	       "<div id=\"divpattern\" class=\"input-group\">" +
	         "<span class=\"input-group-addon\">Pattern</span>" +
	         "<input id=\"iptpattern0\" type=\"text\" class=\"form-control\">	" +	         
	          "<label id=\"addpattern\" class=\"input-group-addon\">+</label>" +
	      "</div>" +
	   "</form>";
    	//$("#consoleinfo").text(code);
    	//$("#divpanelbody").prepare(localform);
    	$("#consoleinfo").text("");
    	$("#fmkp").append(code);
    	
    	// fucking js
    	// it costs me 2 hours to make it work
    	// because .remove cannot delete DOM entirely, it still there occupying room
    	// DOM need to register listen function after rejoining
    	$("#addkeyword").click(function() {
        	
        	var index = keywordlen ++;
        	var code = "<div id=\"divkeyword" + index +"\"class=\"input-group\">" +
    				         "<span id=\"spankeyword"+index+"\"class=\"input-group-addon\">Keyword</span>"+
    				         "<input id=\"iptkeyword"+index+"\" type=\"text\" class=\"form-control\">"+				       
    				      "</div>";
        	
        	$("#fmkp").append( "<br>"+code);
        	consolePrint("Add one keyword div. Code is: <br>", "success");
        	//alert(code);
        });
    	
    	$("#addpattern").click(function() {
        	
        	var index = patternlen ++;
        	var code = "<div id=\"divpattern"+index+"\"class=\"input-group\">" +
    				         "<span id=\"spanpattern"+index+"\" class=\"input-group-addon\">Pattern</span>"+
    				         "<input id=\"iptpattern"+index+"\" type=\"text\" class=\"form-control\">"+				       
    				      "</div>";
        	
        	$("#fmkp").append("<br>"+ code);
        	//alert(code);
        	consolePrint("Add one pattern div. Code is: <br>", "success");
        });
    	
//    	for(var ite = 1; ite < keywordlen; ite ++) {
//    		$("#divkeyword"+ite).remove();
//    		delete $("#divkeyword"+ite);
//    	}
//    	for(var ite = 1; ite < patternlen; ite ++) {
//    		$("#divpattern"+ite).remove();
//    		delete $("#divpattern"+ite);
//    	}
    	
    	keywordlen = 1;
    	patternlen = 1;
    	
    });
    
    $("#btnsearch").click(function() {
    	//consolePrint("Search KP.", "success");
    	var reqdata = collectData();
    	
    	if(reqdata["keywords"] == "[]" && reqdata["patterns"] == "[]") {
    		reqdata["restoreDT"] = "true";
    	} else {
    		reqdata["restoreDT"] = "false";
    	}
    	
    	var loginfo = "[Query]: ";
    	loginfo = loginfo +"<br> keywords: <br>" + reqdata["keywords"];
    	loginfo = loginfo +"<br> patterns: <br>" + reqdata["patterns"];
    	loginfo = loginfo + "<br> Visiting " + serverurl + "TestKP";
    	loginfo = loginfo + "<br> [Result]:<br>";
    	$.ajax( {
			type: "POST",
			url: serverurl + "SearchLog",
			data: reqdata,
			dataType: "json",
			
			//if received a response from the server
			success: function(data, textStatus, jqXHR) {
				
				// get the response data
				if(data.success) {
					loginfo = loginfo + data.matchedLog + " log(s) has been found related to query statement.";
					if(data.matchedLog == "0") {
						consolePrint(loginfo, "danger");
					} else {
						consolePrint(loginfo, "success");
						//reload table
						reloadTable(data.data);
						//alert(data.data.toString());
					}
				} else {
					loginfo = loginfo + "No log is found";
					consolePrint(loginfo, "failed");
				}
				logID = -1;
				$("#log").html("<pre> No Log Is Selected </pre>");
				
			},
			error: function(data, textStatus, jqXHR) {
				//alert("Failed to connect to server, this should not happen");	
			}
		});
    });
    
    $("#btntest").click(function() {
    	var reqdata = collectData();
    	
    	if(reqdata["keywords"] == "[]" && reqdata["patterns"] == "[]") {
    		var loginfo = "[Error] <br> You need to provide at least one" + 
    			" keyword or pattern ";
    		consolePrint(loginfo, "failed");
    		return;
    	}
    	
    	// can be null for now
    	reqdata["logID"] = logID;
    	
    	var loginfo = "[Query]:";
    	loginfo = loginfo +"<br> keywords: <br>" + reqdata["keywords"];
    	loginfo = loginfo +"<br> patterns <br>" + reqdata["patterns"];
    	loginfo = loginfo + "<br> Visiting " + serverurl + "TestKP";
    	loginfo = loginfo + "<br> [Result]:<br>";
    	
    	$.ajax( {
			type: "POST",
			url: "http://10.249.65.27:8080/Ruler/TestKP",
			data: reqdata,
			dataType: "json",
			
			//if received a response from the server
			success: function(data, textStatus, jqXHR) {
				
				// get the response data
				if(data.success) {
					loginfo = loginfo + data.matchTotal + " log(s) has been found related to query statement.";
					if(data.matchCurrentLog) {
						loginfo = loginfo + "<br> Match current log";
					} else {
						loginfo = loginfo + "<br> Do not match current info";
					}
					consolePrint(loginfo, "success");
					
					//do not reload table
				} else {
					loginfo = loginfo + "No log is found";
					consolePrint(loginfo, "failed");
				}
				
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//alert("transmit error");
			}
		});
    	
    });
    
    $("#btnconfirm").click(function() {
    	var reqdata = collectData();
    	
    	var loginfo = "[Add Rule]: ";
    	
    	
    	if($("#iptcategoryname").val() == "") {
    		loginfo = loginfo +"<br>Category Name Must Be Provided";
    		consolePrint(loginfo, "failed");
    		return;
    	}
    	
    	if($("#iptfiltername").val() == "") {
    		loginfo = loginfo + "<br>Filter Name Must Be Provided";
    		consolePrint(loginfo, "failed");
    		return;
    	}
    	
    	if(reqdata["keywords"] == "[]" && reqdata["patterns"] == "[]") {
    		var loginfor = "[Error] <br> You need to provide at least one" + 
    			" keyword or pattern ";
    		consolePrint(loginfor, "failed");
    		return;
    	}
    	
    	$.ajax( {
			type: "POST",
			url: serverurl + "AddRule",
			data: reqdata,
			dataType: "json",
			
			//if received a response from the server
			success: function(data, textStatus, jqXHR) {
				
				// get the response data
				if(data.success) {
					
					if(data.duplicatedFilter) {
						//alert("duplicated filter");
						loginfo = loginfo +"<br> Duplicated filter name, try another one <br>";
						consolePrint(loginfo, "failed");
					} else {
						loginfo = loginfo + "<br>Successfully add one rule to DB <br>";
						loginfo = loginfo + data.size + " logs haven been categoried by this rule";
						consolePrint(loginfo, "success");
						//reload table
						reloadTable(data.data);
						logID = -1;
						$("#log").html("<pre> No Log Is Selected </pre>");
					}
				} else {
					loginfo = loginfo + "<br>Failed to add this rule to DB<br>";
					loginfo = loginfo + "due to duplicated filter name";
					consolePrint(loginfo, "failed");
				}
			},
			error: function(data, textStatus, jqXHR) {
				consolePrint("Failed to Connect server", "failed");
			}
		});
    	
    });   
    

//    $(function() {
//	  	$("#iptcategoryname").autocomplete( {
//	  		source:function(request, response) {
//	  			$.ajax( {
//	  				url: "http://10.249.65.27:8080/Ruler/AutoCompleteCat",
//	  				type: "POST",
//	  				data: {term : request.term},
//	  				dataType: "json",
//	  				success: function(data) {
//	  					response(data);
//	  				}
//	  			});
//	  		}
//	  	});	
//	  });
    
//    $("#iptcategoryname").typeahead({
//    	ajax: "http://10.249.65.27:8080/Ruler/AutoCompleteCat",
//        //remote:"http://10.249.65.27:8080/Ruler/AutoCompleteCat",   	
//   });
    $('#iptcategoryname').typeahead({
        source : function(query, process) {
            jQuery.ajax({
                url : "http://10.249.65.27:8080/Ruler/AutoCompleteCat",
                type : 'POST',
                data : {
                    "query" : query
                },
                dataType : 'json',
                success : function(json) {
                    process(json);
                }
            });
        },
        triggerLength : 0,
    });
    
//   $("#btnconfirm").click(function() {
//	   alert("sss");
//   }) ;
   
   
   $("#btnaddrule").click(function() {
	   //alert("add rule");
	   
//	   <filter name="runMavenBuild: Failed to execute goal org.mortbay.jetty:jetty-maven-plugin" description="N/A">
//       <cause source="what" value="runMavenBuild"/>
//       <cause source="exception" keyword="Failed to execute goal org.mortbay.jetty:jetty-maven-plugin"/>
//       </filter>
	   
	   
	   // &lt; &gt; &quot;
	   
	   var consoleinfo = "";
	   consoleinfo += "&lt;category name=\""+$("#iptcategoryname").val()+"\"&gt;<br>&nbsp; &nbsp;&nbsp; &nbsp;";
	   consoleinfo += "&lt;filter name=\"" + $("#iptfiltername").val() + "\" description=\"" + $("#iptfilterdescription").val() +"\"&gt";
	   
	   for(var i = 0; i < keywordlen; i ++) {
			if($("#iptkeyword"+i).val() != '') {
				//keywords.push($("#iptkeyword"+i).val());
				consoleinfo += "<br> &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;";
				consoleinfo += "&lt;cause keyword=\"" +  $("#iptkeyword"+i).val() + "\"/&gt;";
			}
		}
		
		for(var i = 0; i < patternlen; i ++) {
			if($("#iptpattern"+i).val() != '') {
				//patterns.push($("#iptpattern"+i).val());
				consoleinfo += "<br> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;";
				consoleinfo += "&lt;cause pattern=\"" +  $("#iptpattern"+i).val() + "\"/&gt;";
			}
		}
		consoleinfo += "<br>&nbsp; &nbsp;&nbsp; &nbsp;";
		consoleinfo += "&lt;/filter&gt;<br>";
		consoleinfo += "&lt;category/&gt;";
		$("#ruledetails").html(consoleinfo);
	   
	   $("#myModal").modal('show');
   });
   
    
} );