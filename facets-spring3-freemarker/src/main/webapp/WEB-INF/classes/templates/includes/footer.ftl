</div>

<!-- USERVOICE SCRIPT -->
<script type="text/javascript">
  var uservoiceOptions = {
    key: 'resrev',
    host: 'resrev.uservoice.com', 
    forum: '105381',
    alignment: 'right',
    background_color:'#3287b5', 
    text_color: 'white',
    hover_color: '#3897ca',
    lang: 'en',
    showTab: true
  };
  function _loadUserVoice() {
    var s = document.createElement('script');
    s.src = ("https:" == document.location.protocol ? "https://" : "http://") + "cdn.uservoice.com/javascripts/widgets/tab.js";
    document.getElementsByTagName('head')[0].appendChild(s);
  }
  _loadSuper = window.onload;
  window.onload = (typeof window.onload != 'function') ? _loadUserVoice : function() { _loadSuper(); _loadUserVoice(); };
</script>

</body>
</html>