(function($) {
    $(document).ready(function() {
        

        $('.article-body h6').each(function(index, item) {
            var lineHeight = $(item).css('line-height') || 0;
            var height = $(item).height();
    
            lineHeight =Math.floor(+(lineHeight.substr(0, lineHeight.indexOf('px')))) ;

            if(Math.ceil(height/lineHeight) > 1) {
                $(item).css('text-align', 'left');
            }
        });

    });


})(Zepto);
