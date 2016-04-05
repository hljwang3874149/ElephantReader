function onLoaded() {
    var allImage = document.querySelectorAll("img[esrc]");
    allImage = Array.prototype.slice.call(allImage, 0);
    allImage.forEach(function(image) {
        mWebViewImageListener.loadImage(image.getAttribute("esrc"));
    });
}

function onImageLoadingComplete(pOldUrl, pNewUrl) {
    var allImage = document.querySelectorAll("img[esrc]");
    allImage = Array.prototype.slice.call(allImage, 0);
    allImage.forEach(function(image) {
        if (image.getAttribute("esrc") == pOldUrl || image.getAttribute("esrc") == decodeURIComponent(pOldUrl)) {
            image.src = pNewUrl;
        }
    });
}

function onImageClick(picUrl){
    var allImage = document.querySelectorAll("h6 img[esrc], p img[esrc]");
    allImage = Array.prototype.slice.call(allImage, 0);
    var urls = new Array();
    var index = -1;
    allImage.forEach(function(image) {
        var imgUrl = image.getAttribute("esrc");
        var newLength = urls.push(imgUrl);
        if(imgUrl == picUrl || imgUrl == decodeURIComponent(picUrl)){
            index = newLength-1;
        }
    });
   console.log(urls.toString());
   mWebViewImageListener.onImageClick(urls, index)
}