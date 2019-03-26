# MBrains

<img src="https://raw.githubusercontent.com/NailPlay/MBrains/master/MBrains.gif" width="290" height="490" 
caption="Preview application">


<div class="image-wrapper" >
  {% if include.url %}
  <a href="{{ include.url }}" title="{{ include.title }}" target="_blank">
  {% endif %}
      <img src="{{ site.url }}/{{ include.img }}" alt="{{ include.title }}"/>
  {% if include.url %}
  </a>
  {% endif %}
  {% if include.caption %}
      <p class="image-caption">{{ include.caption }}</p>
  {% endif %}
</div>

## Include the image in your post and specify a caption with this tag
{% include image.html img="https://raw.githubusercontent.com/NailPlay/MBrains/master/MBrains.gif" title="" caption="" %}


