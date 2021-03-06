<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		
		<title>CultWeb | ${museum.name}</title>

		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
		<meta name="description" content="">
		<meta name="keywords" content="">
		<meta name="author" content="">
		
		<link rel="stylesheet" href="<c:url value="/assets/css/materialize.min.css" />">
		<link rel="stylesheet" href="<c:url value="/assets/css/app.css" />">
	</head>
	<body>
	
		<div class="navbar-fixed">
			<nav>
			   <div class="nav-wrapper">
			     <a href="#" class="brand-logo center">CultWeb</a>
			     <ul id="nav-mobile" class="left">
			       <li><a href="<c:url value="/" />">Retour</a></li>
			     </ul>
			   </div>
			 </nav>
		</div>
	
		<article class="container">
		
			<ul class="collection with-header">
				<li class="collection-header name-header"><h4>${museum.name}</h4><i class="medium toggle-favorite"></i></li>
				<li class="collection-item">${museum.contact}</li>
				<li class="collection-item"><a href="${museum.url}" title="Aller sur ${museum.url}" target="_blank">${museum.url}</a></li>
				<li class="collection-item">${museum.address}</li>
				<li class="collection-item">${museum.town}</li>
			</ul>
			
			<div class="row">
				
				<div class="col s12 m6">
					<div id="map"></div>
				</div>
				<div class="col s12 m6">
					
					<h4>Recommendations</h4>
					
					<c:choose>
						
						<c:when test="${not empty museum.recommendations}">
							<ul>
							<c:forEach var="recommendation" items="${museum.recommendations}" varStatus="i" begin="0">
								<li><a href="${recommendation.url}" target="_blank">${recommendation.name}</a></li>
							</c:forEach>
							</ul>
						</c:when>
						<c:otherwise>
							<p>Pas de recommendations.</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		
		</article>
		
		<script src="<c:url value="/assets/js/jquery.min.js" />"></script>
		<script src="<c:url value="/assets/js/materialize.min.js" />"></script>
		<script src="<c:url value="/assets/js/googlemaps.js" />"></script>
		<script src="<c:url value="/assets/js/gmaps.min.js" />"></script>
		
		<script src="<c:url value="/assets/js/favorites.js" />"></script>
		
		<script type="text/javascript">
				
			$(document).ready(function() {
			
				var latitude = "${museum.latitude}";
				var longitude = "${museum.longitude}";
	    	  
				var map = new GMaps({
				    el: '#map',
				    lat: latitude,
				    lng: longitude
				});
				
				map.addMarker({
					lat: latitude,
					lng: longitude,
					title: "${museum.name}"
				});
			    
			    /*
			     * Favorites
			    */
			    
			    var id = "${museum.id}";
			    var name = "${museum.name}";
			    var infrastructureType = "${museum.infrastructure}";
			    
			    var favState = $('.toggle-favorite');
			    
			    var favorites = new Favorites();
			    
			    function toggleFavorite() {
			    	
			    	var isFav = favorites.isFavorite(id);
			    	
			    	if(isFav) {
			    		
			    		favorites.deleteFavorite(id);
			    		
			    		favState.addClass('mdi-toggle-check-box-outline-blank');
			    		favState.removeClass('mdi-toggle-check-box');
			    		
			    	} else {
			    		
			    		favorites.addFavorite(id, name, infrastructureType);
						
			    		favState.addClass('mdi-toggle-check-box');
			    		favState.removeClass('mdi-toggle-check-box-outline-blank');
			    		
			    		toast('<a href="../../favorites/" style="color: #fff;">Votre favori a �t� ajout�</a>', 5500)
			    	}
			    }
			    
			    favState.on('click', toggleFavorite);
			    
			    // Init the state
			    favState.addClass( favorites.isFavorite(id) ? 'mdi-toggle-check-box' : 'mdi-toggle-check-box-outline-blank' );
			});
			
		</script>

	</body>
</html>