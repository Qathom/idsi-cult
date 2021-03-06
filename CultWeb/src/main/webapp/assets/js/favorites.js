/* ==========================================================================
	Favorites Class
	========================================================================== */

'use strict';

var Favorites = (function () {
	
	/**
     * @constructor
    */
	function Favorites() {
		
		this.dbName = 'cultweb_favorites';
		
		var data = this.getFavorites();

		if(data === null) {
			data = [];
			this.save(data);
		}
	}

	Favorites.prototype.addFavorite = function (id, name, type) {
		var data = this.getFavorites();
		data.push( { id: id, name: name, type: type } );
		this.save(data);
	};

	Favorites.prototype.deleteFavorite = function (id) {
		var data = this.getFavorites();
		var i = data.length - 1;
		
		for (i; i >= 0; i--) {
		    var fav = data[i];
		    
		    if(fav.id === id) {
		    	data.splice(i, 1);
		    	break;
		    }
		}
		this.save(data);
	};
	
	Favorites.prototype.isFavorite = function (id) {
		
		var data = this.getFavorites();
		
		var isFav = false;
		
		for (var i = data.length - 1; i >= 0; i--) {
		    var fav = data[i];
		    
		    if(fav.id === id) {
		    	isFav = true;
		    	break;
		    }
		}
		
		return isFav;
	};
	
	Favorites.prototype.getFavorites = function () {
		return JSON.parse(window.localStorage.getItem(this.dbName));
	};
	
	Favorites.prototype.save = function (data) {
		window.localStorage.setItem(this.dbName, JSON.stringify(data));
	};

	return Favorites;
})();