from django import template

from articles.models import Article

register = template.Library()

@register.simple_tag
def featured_article(id):
    '''Gets an article based on id article'''
    return Article.objects.get(pk=id)
