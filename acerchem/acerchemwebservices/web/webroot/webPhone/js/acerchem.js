
function autoLogin()
{
    var userId=$.cookie("userId");
    var password=$.cookie("password");
    var rememberMe=$.cookie("rememberMe");
    if(userId!=null&&password!=null&&rememberMe!=null&&rememberMe)
    {
        $.ajax({
            url: tokenUrl,
            async: true,
            type:'post',
            data:'client_id=mobile_android&client_secret=secret&grant_type=password&username='+userId+'&password='+password,
            dataType: "json",
            crossDomain: true,
            success:function(returndata){
                console.log("success:"+JSON.stringify(returndata));
                $.cookie("access_token","Bearer "+returndata.access_token);
                window.location.href="member.html";
            },
            error:function(returndata){
                console.log("error:"+JSON.stringify(returndata));
                if($.cookie("userId")!=null)
                {
                    document.getElementsByName("username")[0].value=userId;
                }
            }
        });
    }else{
    	window.location.href="sign.html";
    }
    
}

function changePassword()
{
    //alert("123");
    var pwd = /^(?=.*?[0-9])(?=.*?[a-z])(?=.*?[A-Z])[0-9a-zA-Z]{6,16}$/;
    var oldPassword=document.getElementById('oldPassword').value;
    var newPassword=document.getElementById('newPassword').value;
    var confirmPassword=document.getElementById('confirmPassword').value;
    if(oldPassword=='')
    {
        alert('Please fill in the complete!')
        return false;
    }
    else if(newPassword.length<6 || newPassword.length>16 ||!pwd.test(newPassword))
    {
        alert('Password should be a combination of 6-16 lower case letters,uppercase letter and numbers!');
        return false;
    }
    else if(newPassword!=confirmPassword)
    {
        alert('Two passwords are inconsistent!')
        return false;
    }
    else
    {
        $.ajax({
            url:homeUrl+"/users/current/password",
            type:'PUT',
            dataType: "json",
            data:{
                "userId":$.cookie("userId"),
                "old":oldPassword,
                "new":newPassword
            },
            async: true,
            crossDomain: true,
            beforeSend: function(request) {
                request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                request.setRequestHeader("Authorization", $.cookie("access_token"));
            },
            success:function(returndata){
                //console.log("success:"+JSON.stringify(returndata));
                if(returndata.statusText=='Accepted'){window.location.href="member.html";}
                else{alert("Old Password is Incorrect");}
                
            },
            error:function(returndata){
                //console.log("error:"+JSON.stringify(returndata));
                if(returndata.statusText=='Accepted'){window.location.href="member.html";}
                else{alert("Old Password is Incorrect");}
            }
        });
    }
}

function getCreditAccount()
{
    $.ajax({
        url:homeUrl+"/users/"+$.cookie("userId"),
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            console.log("success:"+JSON.stringify(returndata));
            
            var html='';
            html+='<div class="check-status"><span class="status">Status:'+returndata.creditAccount.status+'</span>';
            html+='<div class="check-title"><div class="item"><p>'+returndata.currency.symbol+formatMoney(returndata.creditAccount.creditTotalAmount)+'</p><span>Total Amount</span></div>';
            html+='<div class="item"><p>'+returndata.currency.symbol+formatMoney(returndata.creditAccount.creaditRemainedAmount)+'</p><span>Remained Amount</span></div>';
            html+='<div class="item"><p>'+returndata.creditAccount.billingInterval+'</p><span>Payment Term</span></div></div></div><ul>';
            
            var transactions=returndata.creditAccount.transactions;
            if(transactions!=null)
            {
                for(var i = 0; i < transactions.length; i++){
                    html+='<li><p class="data">'+formatDataTime(transactions[i].creationtime)+'</p>';
                    html+='<div class="m-data bort-bot"><span class="num">'+transactions[i].orderCode+'</span><span class="date">due date ：'+formatData(transactions[i].shouldPaybackTime)+'</span></div>';
                    html+='<div class="m-con"><div class="item"><p>Is Payback</p><span>';
                    if(transactions[i].isPayback)
                    {
                        html+='Yes';
                    }
                    else
                    {
                        html+='No';
                    }
                    html+='</span></div><div class="item"><p>Quantity</p><span>'+transactions[i].productNumber+'</span></div>';
                    html+='<div class="item"><p>Invoice Amount</p><span>'+returndata.currency.symbol+formatMoney(transactions[i].creaditUsedAmount)+'</span></div>';
                    html+='<div class="item"><p>Paid</p><span>';
                    if(transactions[i].paybackAmount!=null)
                    {
                        html+=returndata.currency.symbol+formatMoney(transactions[i].paybackAmount);
                    }
                    else
                    {
                        html+=returndata.currency.symbol+'0.00';
                    }
                    html+='</span></div></div></li>';
                }
            }
            
            html+='</ul>';
            $("#status ul").remove();
            $("#status div").remove();
            $("#status").append(html);
            
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function formatDataTime(fmt) {
	
	fmt = fmt.replace(/\-/g, "/").replace("T"," ").substr(0,19);
	//alert("datetime:" + fmt);

	return fmt;
    //var date = new Date(fmt);
    
    //alert("datetime:" + date);
   // alert("datetime:" +DateFormat.parseDate(new Date(fmt), 'yyyy/MM/dd hh:mm:ss'));
    //var date = DateFormat.parseDate(new Date(fmt), 'yyyy/MM/dd hh:mm:ss');
    //alert("datetime :" + date);
    //return date;
    
    //return date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes();
}

function formatData(fmt) {
    
	
	//fmt = fmt.replace(/\-/g, "/").substr(0,10);
	//alert("date:" + fmt);
	return fmt;
	//var date = new Date(fmt);
	
    //alert("date:" + date);
    //return date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getFullYear();
    
    //alert("date :" + DateFormat.parseDate(new Date(fmt), 'yyyy/MM/dd'));
	//var date = DateFormat.parseDate(new Date(fmt), 'yyyy-MM-dd');
	//alert("date :" + date);
    //return date;
}

function formatMoney(num)  
{  
   num = parseFloat((num + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";  
   var l = num.split(".")[0].split("").reverse(),  
   r = num.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
   return t.split("").reverse().join("") + "." + r;  
} 

function getUser()
{
    $.ajax({
        url:homeUrl+"/users/"+$.cookie("userId"),
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            console.log("success:"+JSON.stringify(returndata));
        	var html= '';
        	if(returndata.userLevel != null) {
        		html +='<p>Hello, '+returndata.name+'</p><span>'+returndata.userLevel.levelName+' Members</span>';
        	}else{
        		html +='<p>Hello, '+returndata.name+'</p><span> Members</span>';
        	}
        	
            
            $("#info p").remove();
            $("#info span").remove();
            $("#info").append(html);
            
            if(returndata.creditAccount.status=='NORMAL')
            {
                var html2='<i></i><span>'+returndata.currency.symbol+returndata.creditAccount.creaditRemainedAmount+'</span>';
                $("#credit span").remove();
                $("#credit i").remove();
                $("#credit").append(html2);
            }
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function getPersonalInfo()
{
    $.ajax({
        url:homeUrl+"/currencies",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            var currency='';
            for(var i = 0; i < returndata.currencies.length; i++)
            {
                currency+='<option value="'+returndata.currencies[i].isocode+'">['+returndata.currencies[i].symbol+']'+returndata.currencies[i].name+'</option>';
            }
            $("#currency option").remove();
            $("#currency").append(currency);
        },
        error:function(returndata){
            console.log("error5:"+JSON.stringify(returndata));
        }
    });
    
    $.ajax({
        url:homeUrl+"/languages",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            var language='';
            for(var i = 0; i < returndata.languages.length; i++)
            {
                language+='<option value="'+returndata.languages[i].isocode+'">'+returndata.languages[i].name+'</option>';
            }
            $("#language option").remove();
            $("#language").append(language);
        },
        error:function(returndata){
            console.log("error4:"+JSON.stringify(returndata));
        }
    });
    
    $.ajax({
        url:homeUrl+"/deliverycountries",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            var country='<option value="">Country</option>';
            for(var i = 0; i < returndata.countries.length; i++){
                country+='<option value="'+returndata.countries[i].isocode+'">'+returndata.countries[i].name+'</option>';
            }
            $("#country option").remove();
            $("#country").append(country); 
        },
        error:function(returndata){
            console.log("error3:"+JSON.stringify(returndata));
        }
    });
    
    $.ajax({
        url:homeUrl+"/users/"+$.cookie("userId"),
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            $("#email span").remove();
            $("#email").append('<span class="name">'+returndata.uid+'</span>');
            $("#companyName span").remove();
            $("#companyName").append('<span class="name">'+returndata.companyName+'</span>');
            document.getElementById('name').value = returndata.name;
            $("#language option[value='"+returndata.language.isocode+"']").attr("selected", true);
            $("#currency option[value='"+returndata.currency.isocode+"']").attr("selected", true);
        },
        error:function(returndata){
            console.log("error2:"+JSON.stringify(returndata));
        }
    });
    
    $.ajax({
        url:homeUrl+"/users/current/contactAddress",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("contactAddress:"+JSON.stringify(returndata));
			 alert("contactAddress:"+JSON.stringify(returndata));
            $("#country option[value='"+returndata.country.isocode+"']").attr("selected", true);
            
            document.getElementById('townCity').value = returndata.town;
            document.getElementById('addressId').value = returndata.id;
            console.log("country:"+document.getElementById('country').value);
            alert("country:"+document.getElementById('country').value);
            getRegions(returndata.country.isocode,returndata.region.isocode);
        },
        error:function(returndata){
            console.log("error11:"+JSON.stringify(returndata));
        }
    });
    
    
}

function updatePersonalInfo()
{
    var name=document.getElementById('name').value;
    var language = document.getElementById('language').value;
    var currency = document.getElementById('currency').value;
    var country = document.getElementById('country').value;
    var region = document.getElementById('region').value;
    var town=document.getElementById('townCity').value;
    var addressId=document.getElementById('addressId').value;
    if(name!=''&&language!=''&&currency!=''&&country!=''&&region!=''&&town!=''&&addressId!='')
    {
        $.ajax({
            url:homeUrl+"/users/current",
            type:'put',
            dataType: "json",
            data:{
                "name":name,
                "language":language,
                "currency":currency,
                "addressId":addressId,
                "country":country,
                "region":region,
                "town":town
            },              
            async: true,
            crossDomain: true,
            beforeSend: function(request) {
                request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                request.setRequestHeader("Authorization", $.cookie("access_token"));
            }
        });
        window.location.href="member.html";
    }
    else
    {
        alert('Please complete the information!')
        return false;
    }
}

function getPromotionItem()
{
    $.ajax({
        url:homeUrl+"/products/promotionItem",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            var html='';
            var products=returndata.products;
            for(var i = 0; i < products.length; i++){
                html+='<li class="swiper-slide swiper-slide-visible swiper-slide-active" style="width: 7.5rem; height: 8.25rem"><a href="product.html?code='+products[i].code+'">';
                if(products[i].images != null) {
                	html+='<div class="maximg"><img   src="'+products[i].images[0].url.substring(23)+'"></div><p>['+products[i].code+']'+products[i].name+'</p><span>';
                }else{
                	html+='<div class="maximg"><img   src=""></div><p>['+products[i].code+']'+products[i].name+'</p><span>';
                }
                
                if(products[i].promotionPrice!=null)
                {
                    html+=products[i].promotionPrice.formattedValue;
                }
                else if(products[i].price!=null)
                {
                    html+=products[i].price.formattedValue;
                }
                html+='</span></a></li>';
            }
            $("#promotionItem li").remove();
            $("#promotionItem").append(html); 
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function getAddresses()
{
    $.ajax({
        url:homeUrl+"/users/current/addresses",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            var html='';
            if(returndata.addresses!=null)
            {
                for(var i = 0; i < returndata.addresses.length; i++)
                {
                    if(returndata.addresses[i].defaultAddress==true)
                    {
                        html+='<li class="def">';
                    }
                    else
                    {
                        html+='<li>';
                    }
                    html+='<div class="center borbot-darsh"><h4>'+returndata.addresses[i].lastName+'</h4>';
                    if(returndata.addresses[i].region != null) {
                    	html+='<p>'+returndata.addresses[i].town+','+returndata.addresses[i].region.name+',';
                    }else{
                    	html+='<p>'+returndata.addresses[i].town+',';
                    }
                    
                    if(returndata.addresses[i].country != null) {
                    	html+=returndata.addresses[i].country.name+'</p>';
                    }else{
                    	html+='</p>';
                    }
                    
                    html+='<p>'+returndata.addresses[i].phone+'/'+returndata.addresses[i].phone2+'</p></div>';
                    html+='<div class="handle"><div class="radio" onclick="defaultAddressById('+returndata.addresses[i].id+')"><div class="con-invo"></div><span>Default</span></div>';
                    html+='<div class="hand"><span class="edit editaddres-popup" onclick="getAddressById('+returndata.addresses[i].id+')"></span><span class="delect" onclick="delAddressById('+returndata.addresses[i].id+')"></span></div></div></li>';   
                    $("#address li").remove();
                    $("#address").append(html);
                }
            }
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function getAddressById(AddressId)
{
    $.ajax({
        url:homeUrl+"/users/current/addresses/"+AddressId,
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            getRegions(returndata.country.isocode,returndata.region.isocode);
            document.getElementById('addressId').value = returndata.id;
            document.getElementsByName('contact')[1].value = returndata.lastName;
            document.getElementsByName('mobilePhone')[1].value = returndata.phone2;
            document.getElementsByName('telephone')[1].value = returndata.phone;
            document.getElementsByName('townCity')[1].value = returndata.town;
            document.getElementsByName('zipCode')[1].value = returndata.postalCode;
            $("#country2 option[value='"+returndata.country.isocode+"']").attr("selected", true);
            $(".g-editaddres-popup").show();
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function getCountries()
{
    $.ajax({
        url:homeUrl+"/deliverycountries",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            var html='<option value="">Country</option>';
            for(var i = 0; i < returndata.countries.length; i++){
                html+='<option value="'+returndata.countries[i].isocode+'">'+returndata.countries[i].name+'</option>';
            }
            $("#country option").remove();
            $("#country").append(html); 
            $("#country2 option").remove();
            $("#country2").append(html); 
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

$(document).on("change",'#country', function (){
    getRegions($(this).val(),'');
})

$(document).on("change",'#country2', function (){
    getRegions($(this).val(),'');
})

function getRegions(countryIsoCode,regionIsocode)
{
    $.ajax({
        url:homeUrl+"/deliveryRegions",
        type:'get',
        data:'countryIsoCode='+countryIsoCode,
        dataType: "json",
        async: true,
        crossDomain: true,
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            $("#region option").remove();
            $("#region2 option").remove();
            var html='<option value="">Region</option>';
            if(returndata.regions!=null)
            {
                for(var i = 0; i < returndata.regions.length; i++){
                    html+='<option value="'+returndata.regions[i].isocode+'">'+returndata.regions[i].name+'</option>';
                }
                $("#region").append(html); 
                $("#region2").append(html);
                $("#region option[value='"+regionIsocode+"']").attr("selected", true);
                $("#region2 option[value='"+regionIsocode+"']").attr("selected", true);
                
                console.log("region:"+ document.getElementById('region').value);
                alert("region:"+ document.getElementById('region').value);
            }
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

$(".radio").on('click',function(){
    $(this).parents('li').addClass('def').siblings().removeClass('def');
})

function newAddress()
{
    var contact = document.getElementsByName('contact')[0].value;
    var mobilePhone = document.getElementsByName('mobilePhone')[0].value;
    var telephone = document.getElementsByName('telephone')[0].value;
    var country = document.getElementById('country').value;
    var region = document.getElementById('region').value;
    var townCity = document.getElementsByName('townCity')[0].value;
    var zipCode = document.getElementsByName('zipCode')[0].value;
    if(contact==''||mobilePhone==''||telephone==''||country==''||townCity==''||zipCode=='')
    {
        alert('Please complete the information!')
        return false;
    }
    else
    {
        $.ajax({
            url:homeUrl+"/users/current/addresses",
            type:'post',
            dataType: "json",
            data:{
                //"defaultAddress":true,
                "lastName":contact,
                "mobilePhone":mobilePhone,
                "phone":telephone,
                "country":country,
                "region":region,
                "town":townCity,
                "postalCode":zipCode
            },              
            async: true,
            crossDomain: true,
            beforeSend: function(request) {
                request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                request.setRequestHeader("Authorization", $.cookie("access_token"));
            },
            success:function(returndata){
                console.log("success:"+JSON.stringify(returndata));
                window.location.reload();
            },
            error:function(returndata){
                console.log("error:"+JSON.stringify(returndata));
            }
        });
        return false; 
    }
}

function updateAddress()
{
    var contact = document.getElementsByName('contact')[1].value;
    var mobilePhone = document.getElementsByName('mobilePhone')[1].value;
    var telephone = document.getElementsByName('telephone')[1].value;
    var addressId = document.getElementById('addressId').value;
    var country = document.getElementById('country2').value;
    var region = document.getElementById('region2').value;
    var townCity = document.getElementsByName('townCity')[1].value;
    var zipCode = document.getElementsByName('zipCode')[1].value;
    if(contact==''||mobilePhone==''||telephone==''||country==''||townCity==''||zipCode=='')
    {
        alert('Please complete the information!')
        return false;
    }
    else
    {
        $.ajax({
            url:homeUrl+"/users/current/addresses/"+addressId,
            type:'put',
            dataType: "json",
            data:{
                "id":addressId,
                "lastName":contact,
                "mobilePhone":mobilePhone,
                "phone":telephone,
                "country":country,
                "region":region,
                "town":townCity,
                "postalCode":zipCode
            },              
            async: true,
            crossDomain: true,
            beforeSend: function(request) {
                request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                request.setRequestHeader("Authorization", $.cookie("access_token"));
            },
            success:function(returndata){
                console.log("success:"+JSON.stringify(returndata));
                if(returndata.statusText=="OK")window.location.reload();
            },
            error:function(returndata){
                console.log("error:"+JSON.stringify(returndata));
                if(returndata.statusText=="OK")window.location.reload();
            }
        });
        return false; 
    }
}

function delAddressById(addressId)
{
    var wrap = $(this).parents('li');
    var mess = 'Whether to delete this address ?';
    $('.newadd').hide();
    
    //删除提示
    var aele = '<div class="delpop"> <div class="delcont"> <div class="title bort-bot"> Tips </div> <div class="text"> '+mess+'</div><div class="btn-set"><a class="btn btn-line" href="javascript:void(0)">Cancel</a><a class="btn btn-del" href="javascript:void(0)">Confirm</a></div></div>';

    if($('.maxpop').length>0){
        return false;
    }
    $('body').after(aele);

    $('.btn').on('click',function(){
        var aval = $(this).text();
        switch(aval){
            case 'Cancel' :
                $('.delpop').remove();
                break;
            case 'Confirm' :
                $.ajax({
                    url:homeUrl+"/users/current/addresses/"+addressId,
                    type:'DELETE',
                    dataType: "json",
                    async: true,
                    crossDomain: true,
                    beforeSend: function(request) {
                        request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                        request.setRequestHeader("Authorization", $.cookie("access_token"));
                    },
                    success:function(returndata){
                        console.log("success:"+JSON.stringify(returndata));
                        if(returndata.statusText=="OK")window.location.reload();
                    },
                    error:function(returndata){
                        console.log("error:"+JSON.stringify(returndata));
                        if(returndata.statusText=="OK")window.location.reload();
                    }
                });
        }
    })
}

function defaultAddressById(addressId)
{
    $.ajax({
        url:homeUrl+"/users/current/setDefaultAddress/"+addressId,
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        }
    });
    window.location.reload();
}


function getOrder()
{

    $.ajax({
        url:homeUrl+"/users/current/orders",
        type:'get',
        dataType: "json",
        data:{
            "orderCode":orderCode,
            "sort":sort,
            "statuses":statuses
        },
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            updateHtml(returndata);
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function updateHtml(returndata){
    var html='';
    $("#orders li").remove();
    if(returndata.orders!=null)
    {
    	console.log("success:"+JSON.stringify(returndata));
        for(var i = 0; i < returndata.orders.length; i++){
        	
            html+='<li data-Total="' + returndata.orders[i].total.formattedValue + '"><div class="m-col"><div class="m-data bort-bot"><a href="member-order-detailed.html?'+returndata.orders[i].code+'"><span class="num">'+returndata.orders[i].code+'</span>';
            html+='<span class="date">due date ：'+returndata.orders[i].waitDeliveiedDate+'</span></a></div><div class="m-con"><div class="item item-text"><p>';
            html+=formatDataTime(returndata.orders[i].placed)+'</p><span>'+returndata.orders[i].total.formattedValue+'</span></div><div class="item g-succbut">';
            if(returndata.orders[i].status=='CHECKED_VALID')
            {
                html+='<div class="but"><button class="fund">UNCONFIRMED</button></div>';
            }
            else if(returndata.orders[i].status=='UNDELIVERED'||returndata.orders[i].status=='DELIVERED')
            {
                html+='<div class="but"><button class="fund">PROCESSING</button></div>';
            }
            else if(returndata.orders[i].status=='CANCELLED')
            {
                html+='<div class="but"><button class="fund">REFUND</button></div>';
            }
            else
            {
                html+='<div class="but"><button class="fund">'+returndata.orders[i].status+'</button></div>';
            }
            html+='</div></div></div></li>';
        }
        $("#orders").append(html); 
    }
}

function searchOrder(){
    $.cookie("orderCode",document.getElementById('key').value);
    window.location.href="member-order.html";
}

function getTickets()
{
    $.ajax({
        url:homeUrl+"/tickets",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            console.log("success:"+JSON.stringify(returndata));
            var html='';
            if(returndata.tickets!=null)
            {
                for(var i = 0; i < returndata.tickets.length; i++)
                {
                    html+='<li onclick="window.location.href=\'ticket-detail.html?'+returndata.tickets[i].id+'\'"><p class="data">'+formatDataTime(returndata.tickets[i].creationDate)+'</p>';
                    html+='<div class="m-data bort-bot"><span class="num">'+returndata.tickets[i].id+'</span><span class="date"></span></div>';
                    html+='<div class="m-con">';
                    html+='<div class="item" style="width:33%;"><p>Subject</p><span>'+returndata.tickets[i].subject+'</span></div>';
                    html+='<div class="item" style="width:33%;"><p>Status</p><span>'+returndata.tickets[i].status.id+'</span></div>';
                    html+='<div class="item" style="width:33%;"><p>Last modified</p><span>'+formatDataTime(returndata.tickets[i].lastModificationDate)+'</span></div>';
                    html+='</div></li>';
                }
            }
            $("#tickets li").remove();
            $("#tickets").append(html);
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function addTicket()
{
    var yourname=document.getElementsByName('yourname')[0].value;
    var telephone=document.getElementsByName('telephone')[0].value;
    var address=document.getElementsByName('address')[0].value;
    var email=document.getElementsByName('email')[0].value;
    var message=document.getElementsByName('message')[0].value;
    
    var tel = /^\d{1,}$/;
    var ema = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    

    
    if(yourname==''||telephone==''||address==''||email==''||message=='')
    {
        alert('Please fill in the complete!')
        return false;
    }
    else
    {
        if(!tel.test(telephone)){
       	 alert('Please enter a valid telephone!')
            return false;
       }
       
       if(!ema.test(email)) {
       	alert('Please enter a valid email!')
           return false;
       }
        $.ajax({
            url:homeUrl+"/tickets/addTicket",
            type:'post',
            dataType: "json",
            data:{
                "yourname":yourname,
                "telephone":telephone,
                "address":address,
                "email":email,
                "subject":"Home Advisory",
                "message":message,
                "productId":"",
                "productName":""
            },
            async: true,
            crossDomain: true,
            beforeSend: function(request) {
                request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                request.setRequestHeader("Authorization", $.cookie("access_token"));
            },
            error:function(returndata){
            	console.log("error:"+JSON.stringify(returndata));
                if(returndata.statusText=='Created')
                {
                    window.location.href="tickets.html";
                }
                else
                {
                    alert("error:"+returndata.responseText);
                }
            }
        });
    }
}

function showTicket()
{
    $.ajax({
        url:homeUrl+"/tickets/updateTicket/"+ticketId,
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            ticketStatus=returndata.status.id;
            
            var html='';
            var events=returndata.ticketEvents;
            for(var i = events.length-1; i >=0; i--)
            {
                if(events[i].addedByAgent)
                {
                    html+='<div class="atalk">Customer Support on '+formatDataTime(events[i].startDateTime)+'<br/><span id="asay">'+events[i].text+'</span></div>';
                }
                else
                {
                    html+='<div class="btalk">'+events[i].author+' on '+formatDataTime(events[i].startDateTime)+'<br/><span id="bsay">'+events[i].text+'</span></div>';
                }
            }
            $("#words div").remove();
            $("#words").append(html);
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function updateTicket()
{
    var message=document.getElementsByName('message')[0].value;
    if(message=='')
    {
        alert('Please fill in the complete!')
        return false;
    }
    else
    {
        $.ajax({
            url:homeUrl+"/tickets/updateTicket/"+ticketId,
            type:'post',
            dataType: "json",
            data:{
                "ticketStatus":ticketStatus,
                "message":message
            },
            async: true,
            crossDomain: true,
            beforeSend: function(request) {
                request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                request.setRequestHeader("Authorization", $.cookie("access_token"));
            },
            success:function(returndata){
                //console.log("success:"+JSON.stringify(returndata));
                var html='';
                var events=returndata.ticketEvents;
                for(var i = events.length-1; i >=0; i--)
                {
                    if(events[i].addedByAgent)
                    {
                        html+='<div class="atalk">Customer Support on '+formatDataTime(events[i].startDateTime)+'<br/><span id="asay">'+events[i].text+'</span></div>';
                    }
                    else
                    {
                        html+='<div class="btalk">'+events[i].author+' on '+formatDataTime(events[i].startDateTime)+'<br/><span id="bsay">'+events[i].text+'</span></div>';
                    }
                }
                $("#words div").remove();
                $("#words").append(html);
                document.getElementsByName('message')[0].value='';
            },
            error:function(returndata){
                console.log("error:"+JSON.stringify(returndata));
            }
        });
    }
}

function showOrder()
{
    $.ajax({
        url:homeUrl+"/users/current/orders/"+orderId,
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            var html='<div class="g-cart-popup succ-list"><ul class="chick-pro">';
            html+='<li><span>Order Number</span><p>'+returndata.code+'</p></li>';
            html+='<li><span>Order Status</span><p>';
            if(returndata.status=='CHECKED_VALID')
            {
                html+='UNCONFIRMED';
            }
            else if(returndata.status=='UNDELIVERED'||returndata.status=='DELIVERED')
            {
                html+='PROCESSING';
            }
            else if(returndata.status=='CANCELLED')
            {
                html+='REFUND';
            }
            else
            {
                html+=returndata.status;
            }
            html+='</p></li><li><span>Date Placed</span><p>'+formatDataTime(returndata.created)+'</p></li>';
            html+='<li class="gen-price"><span>Total</span><p>'+returndata.totalPrice.formattedValue+'</p></li></ul>';
            html+='<div class="check-add addres-popup"><div class="left"><i class="maxlef"></i></div>';
            html+='<div class="center"><h4>'+returndata.deliveryAddress.lastName+'</h4><p>'+returndata.deliveryAddress.town+'</p><p>'+returndata.deliveryAddress.region.name+','+returndata.deliveryAddress.country.name+','+returndata.deliveryAddress.phone+'</p></div></div>';
            html+='<ul class="pro-list bort-top">';
            for(var i = 0; i <returndata.entries.length; i++)
            {
                html+='<li class="bort-bot"><a href="product.html?'+returndata.entries[i].product.code+'"><div class="maximg"><img src="'+returndata.entries[i].product.images[0].url.substring(23)+'"></div><div class="text">';
                html+='<p>'+returndata.entries[i].product.name+'</p><span class="prompt">ID:'+returndata.entries[i].product.code+'</span><span class="prompt">Volume:'+returndata.entries[i].product.netWeight*returndata.entries[i].quantity+returndata.entries[i].product.unitName+'</span>';
                html+='<div class="price"><span>'+returndata.entries[i].basePrice.formattedValue+'</span><em><span>x</span><b>'+returndata.entries[i].quantity+'</b></em></div></div></a></li>';
            }
            html+='</ul><div class="gall-pag"><p><i></i><span>Part of your order qualifies for FREE Shipping.</span></p><p><i></i><span>All goods Sales to 10% Off; </span></p></div>';
            html+='<div class="succ-type succ-type1"><h5>Payment Type</h5><p>'+returndata.paymentMode+'</p></div>';
            html+='<div class="succ-type succ-type1"><h5>Delivery Methods</h5><p>';
            if(returndata.deliveryMode.code=='DELIVERY_GROSS')
            {
                html+='DDP';
            }
            else
            {
                html+='FCA';
            }
            html+='</p></div></div><div class="succ-prosi"><ul class="chick-pro"><li><span>Subtotal</span><p>';
            html+=returndata.subTotal.formattedValue.substring(0,1)+formatMoney(returndata.subTotal.value+returndata.totalDiscounts.value);
            html+='</p></li><li><span>Delivery</span><p>'+returndata.deliveryCost.formattedValue+'</p></li>';
            html+='<li><span>Release Cost</span><p>'+returndata.storageCost.formattedValue+'</p></li><li><span>Handling Charge</span><p>'+returndata.operateCost.formattedValue+'</p></li>';
            html+='<li><span>Discount Amount</span><p>-'+returndata.totalDiscounts.formattedValue+'</p></li><li class="gen-price bort-top"><span>Order Total</span><p>'+returndata.totalPrice.formattedValue+'</p></li></ul></div>';
            $("#detail div").remove();
            $("#detail").append(html);
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    }); 
}

function getPromotionItemforProductPage()
{
    $.ajax({
        url:homeUrl+"/products/promotionItem",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            var html='';
            var products=returndata.products;
            for(var i = 0; i < products.length; i++){
                html+='<li class="swiper-slide swiper-slide-visible swiper-slide-active" style="width: 160px; height: 194px;"><a href="product.html?code='+products[i].code+'">';
                if(products[i].images != null) {
                	html+='<div class="maximg" "><img src="'+products[i].images[0].url.substring(23)+'"></div><p>'+products[i].name+'</p><span>';
                }else{
                	html+='<div class="maximg" "><img src=""></div><p>'+products[i].name+'</p><span>';
                }
                
                if(products[i].promotionPrice!=null)
                {
                    html+=products[i].promotionPrice.formattedValue;
                }
                else if(products[i].price!=null)
                {
                    html+=products[i].price.formattedValue;
                }
                html+='</span></a></li>';
            }
            $("#promotionItem").append(html); 
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function getAllProducts()
{
    $.ajax({
        url:homeUrl+"/products/show",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            var html='';
            var products=returndata.products;
            for(var i = 0; i < products.length; i++){
            	
            	if( products[i].name != undefined) {
            		html+='<li data-name="' + products[i].name + '"><div class="maximg"><a href="product.html?code='+products[i].code+'">';
//                  html+='<li><div class="maximg"><a href="product.html">';
//                   html+='<li><div class="maximg"><a href="'+products[i].url+'">';
                     if(products[i].images != null) {
                     	html+='<img src="'+products[i].images[0].url.substring(23)+'"></a></div><div class="text g-price"><a href="product.html?code='+products[i].code+'"><p>'+products[i].name+'</p><div class="price"><span>';
                     }else {
                     	html+='<img src=""></a></div><div class="text g-price"><a href="product.html?code='+products[i].code+'"><p>'+products[i].name+'</p><div class="price"><span>';
                     }
                    
                     if(products[i].promotionPrice!=null){
                         html+=products[i].promotionPrice.formattedValue + '</span><em>';
                     }else{
                         html+='</span><em>';
                     }
                     
                     if(products[i].price!=null){
                         html+=products[i].price.formattedValue+'</em></div></a></div></li>'
                     }else{
                         html+='</em></div></a></div></li>'
                     }
            	}else{
            		console.log("name undefined, code"+ products[i].code);
            	}
            	
            }
            $("#productLists").append(html); 
            
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function getProductsByCode(code)
{
    $.ajax({
        url:homeUrl+"/products/productCode/"+code,
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            console.log("success:"+JSON.stringify(returndata));
            var productImages = '';
            var images = returndata.images;
            if(returndata.images != null) {
                for(var i = 0; i < images.length; i++) {
                    //productImages += '<li class="swiper-slide"><img src="' + images[i].url.substring(23)+ '" alt="' + images[i].altText +'"></li>';
                    //console.log("success:"+images[i].url.substring(23));
                    if(images[i].galleryIndex != null) {
                        if(images[i].format == 'product') {
                            productImages += '<li class="swiper-slide"><img src="' + images[i].url.substring(23)+ '" alt="' + images[i].altText +'"></li>';
                            //console.log("success:"+images[i].url.substring(23));
                        }
                        
                    }
                    
                }
            }

            $("#productImages").append(productImages);
            
            var baseInfo = '';
            baseInfo += '<div class="price"><div class="left"><b>';
            if(returndata.promotionPrice!=null){
                baseInfo+=returndata.promotionPrice.formattedValue;
            }
            baseInfo+='</b><em>';
            if(returndata.price!=null){
                baseInfo+=returndata.price.formattedValue;
            }
            baseInfo+='</em></div><div class="right"><i class="icon"></i><span>' + returndata.code + 
            '</span></div></div><p>' + returndata.name + '</p><span>' + returndata.summary + '</span>';
            $("#baseInfo").append(baseInfo);
            
            var saleInfo = '';
            var potentialPromotions = returndata.potentialPromotions;
            if(potentialPromotions != null) {
                for(var i =0; i < potentialPromotions.length; i++){
                    var firedMessages = potentialPromotions[i].firedMessages;
                    if(firedMessages != null) {
                        for(var j= 0; j < firedMessages.length; j++) {
                            var info = firedMessages[j];
                            var infoResult = info.split(",");
                            saleInfo += infoResult[0] + '% off order more than ' + infoResult[1] + ' ' + returndata.packageType + 's <br/>';
                        }
                    }
                }
            }
            $("#saleInfo").append(saleInfo);
            
            
            
            var documents = '';
            var certificatess = returndata.certificatess;
            if(certificatess != null) {
                for(var i =0; i < certificatess.length; i++) {
                    documents += '<li><a class="m-con" href="' + certificatess[i].url + '" ><span>' + certificatess[i].altText + '</span><i></i></a></li>';
                }
            }
            
            $("#documents").append(documents);
            
            var description='';
            description+='<p>' + returndata.description + '</p>';
            $("#productDescription").append(description);
            
            
            var additionInfo ='';
            additionInfo +='<tr><td class="left">Other Names</td><td class="right">' + returndata.otherName + '</td></tr>';
            additionInfo +='<tr><td class="left">Quality Standard</td><td class="right">' + returndata.specification + '</td></tr>';
            additionInfo +='<tr><td class="left">CAS</td><td class="right">' + returndata.CAS + '</td></tr>';
            additionInfo +='<tr><td class="left">Chemical Formula</td><td class="right">' + returndata.chemicalInfo + '</td></tr>';
            additionInfo +='<tr><td class="left">Molecular Weight</td><td class="right">' + returndata.formulaWeight + '</td></tr>';
            additionInfo +='<tr><td class="left">Manufacturer</td><td class="right">' + returndata.manufacturer + '</td></tr>';
            additionInfo +='<tr><td class="left">Package Type</td><td class="right">' + returndata.netWeight +returndata.unitName + '/'+  returndata.packageType +'</td></tr>';
            $("#additionalInfo").append(additionInfo);
            
           /*
            var code = '<span class="news" onclick= "window.location.href=\'productticket-add.html?code=' + returndata.code + '\';"><i></i></span>';
            
            $("#news").append(code);
            */
            
            
            var ProName = '';
            var ProCode = '';
            ProName += '<input type="text" readonly="readonly" name="ProName" value="' +  returndata.name  + '">';
            ProCode += '<input type="text" readonly="readonly" name="ProCode" value="' +  returndata.code  + '">';
            $("#ProName").append(ProName);
            $("#ProCode").append(ProCode);
            
            var userId = $.cookie("userId");
            
            if(userId != undefined && userId != 'null') {
                $.ajax({
                    url:homeUrl+"/users/"+$.cookie("userId"),
                    type:'get',
                    dataType: "json",
                    async: true,
                    crossDomain: true,
                    beforeSend: function(request) {
                        request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                        request.setRequestHeader("Authorization", $.cookie("access_token"));
                    },
                    success:function(returndata){
                        //console.log("success:"+JSON.stringify(returndata));
                    	var Name = '';
                        var Email = '';
                        Name += '<input type="text"  name="Name" placeholder="Your Name" value="' +  returndata.name  + '">';
                        Email += '<input type="text" name="Email" placeholder="Email" value="' +  returndata.uid  + '">';
                        $("#Name").append(Name);
                        $("#Email").append(Email);
                    },
                    error:function(returndata){
                    	var Name = '';
                        var Email = '';
                        Name += '<input type="text"  name="Name" placeholder="Your Name" value="">';
                        Email += '<input type="text" name="Email" placeholder="Email" value="">';
                        $("#Name").append(Name);
                        $("#Email").append(Email);
                    }
                });
            }else{
            	
            	var Name = '';
                var Email = '';
                Name += '<input type="text"  name="Name" placeholder="Your Name" value="">';
                Email += '<input type="text" name="Email" placeholder="Email" value="">';
                $("#Name").append(Name);
                $("#Email").append(Email);
            } 

          
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function getTicketInfo(code){
	$.ajax({
        url:homeUrl+"/products/productCode/"+code,
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            console.log("success:"+JSON.stringify(returndata));  
            var ProName = '';
            var ProCode = '';
            ProName += '<input type="text" readonly="readonly" name="ProName" value="' +  returndata.name  + '">';
            ProCode += '<input type="text" readonly="readonly" name="ProCode" value="' +  returndata.code  + '">';
            $("#ProName").append(ProName);
            $("#ProCode").append(ProCode);
          
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
	});
    
    if($.cookie("userId") != 'null') {
        $.ajax({
            url:homeUrl+"/users/"+$.cookie("userId"),
            type:'get',
            dataType: "json",
            async: true,
            crossDomain: true,
            beforeSend: function(request) {
                request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                request.setRequestHeader("Authorization", $.cookie("access_token"));
            },
            success:function(returndata){
                //console.log("success:"+JSON.stringify(returndata));
            	var Name = '';
                var Email = '';
                Name += '<input type="text"  name="Name" placeholder="Your Name" value="' +  returndata.name  + '">';
                Email += '<input type="text" name="Email" placeholder="Email" value="' +  returndata.uid  + '">';
                $("#Name").append(Name);
                $("#Email").append(Email);
            },
            error:function(returndata){
            	var Name = '';
                var Email = '';
                Name += '<input type="text"  name="Name" placeholder="Your Name" value="">';
                Email += '<input type="text" name="Email" placeholder="Email" value="">';
                $("#Name").append(Name);
                $("#Email").append(Email);
            }
        });
    }else{
    	var Name = '';
        var Email = '';
        Name += '<input type="text"  name="Name" placeholder="Your Name" value="">';
        Email += '<input type="text" name="Email" placeholder="Email" value="">';
        $("#Name").append(Name);
        $("#Email").append(Email);
    } 
}

function addProductTicket()
{
    var yourname=document.getElementsByName('Name')[0].value;
    var telephone=document.getElementsByName('Phone')[0].value;
    var address=document.getElementsByName('Address')[0].value;
    var email=document.getElementsByName('Email')[0].value;
    var message=document.getElementsByName('Message')[0].value;
    var proCode = document.getElementsByName('ProCode')[0].value;
    var proName = document.getElementsByName('ProName')[0].value;
    
    var tel = /^\d{1,}$/;
    var ema = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    
    if(yourname==''||telephone==''||address==''||email==''||message=='')
    {
        alert('Please fill in the complete!')
        return false;
    }
    else
    {
        if(!ema.test(email)) {
        	alert('Please enter a valid email!')
            return false;
        }
        if(!tel.test(telephone)){
        	 alert('Please enter a valid telephone!')
             return false;
        }
        $.ajax({
            url:homeUrl+"/tickets/addTicket",
            type:'post',
            dataType: "json",
            data:{
                "yourname":yourname,
                "telephone":telephone,
                "address":address,
                "email":email,
                "subject":proCode,
                "message":message,
                "productId":proCode,
                "productName":proName
            },
            async: true,
            crossDomain: true,
            beforeSend: function(request) {
                request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                request.setRequestHeader("Authorization", $.cookie("access_token"));
            },
            
            error:function(returndata){
            	//console.log("error:"+JSON.stringify(returndata));
            	//alert("error" + returndata.statusText);
            	window.location.href ="product.html?code="+proCode;
            }
            
        });
    }
}

function getCatalogs()
{
    $.ajax({
        url:homeUrl+"/catalogs",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            //console.log("success:"+JSON.stringify(returndata));
            var html ='';
            var categories1 = '<li class=""><div class="title"><h4>Shop by Category</h4><em></em><i class="dow-top"></i></div><ul class="gallery-art">';
            var categories2 = '<li class=""><div class="title"><h4>Shop by Category</h4><em></em></div><ul class="gallery-art">';
            var catalogs = returndata.catalogs;
            var catalogVersions=catalogs[0].catalogVersions;
            
            console.log("success:"+JSON.stringify(catalogVersions));
            for(var i = 0; i < catalogVersions.length; i++) {
                if(catalogVersions[i].id == 'Online') {
                    var categories = catalogVersions[i].categories;
                    for(var j = 0;j < categories.length; j++) {
                        if(categories[j].id=='2') {
                            var subcategories2= categories[j].subcategories;
                            for(var r=0;r<subcategories2.length; r++){
                                categories2 +='<li><span>' + subcategories2[r].name + '</span></li>';
                            }
                            categories2 +='</ul></li>';
                        }else if(categories[j].id=='1') {
                            var subcategories1= categories[j].subcategories;
                            for(var r=0;r<subcategories1.length; r++){
                                categories1 +='<li><span>' + subcategories1[r].name + '</span></li>';
                            }
                            categories1 +='</ul></li>';
                        }
                    }
                }
            }
            
            $("#categories").append(categories2);
            $("#categories").append(categories1);
            
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function getProductInvenroty(code)
{
    $.ajax({
        url:homeUrl+"/products/inventory/"+code,
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            console.log("success:"+JSON.stringify(returndata));
            var storeOfProductDataList = returndata.returndata;
            if(storeOfProductDataList != null) {
            	for(var i = 0; i < storeOfProductDataList.length; i++) {
            		
            	}
            }
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

function getCategories(code) {
    $.ajax({
        url:homeUrl+"/catalogs/category/571",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            console.log("success:"+JSON.stringify(returndata));

        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

/*
function getCategories(code) {
    $.ajax({
        url:homeUrl+"/products/search?query=category:571",
        type:'get',
        dataType: "json",
        async: true,
        crossDomain: true,
        
        beforeSend: function(request) {
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("Authorization", $.cookie("access_token"));
        },
        success:function(returndata){
            console.log("success:"+JSON.stringify(returndata));
            
        },
        error:function(returndata){
            console.log("error:"+JSON.stringify(returndata));
        }
    });
}

*/




