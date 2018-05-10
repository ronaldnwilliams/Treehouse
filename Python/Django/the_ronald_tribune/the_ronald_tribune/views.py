from django.shortcuts import render

from articles.models import Article

def index(request):
    featuredArticle = Article.objects.get(title__icontains='skills')
    articles = Article.objects.all()
    return render(request, 'index.html', {'articles': articles, 'featuredArticle': featuredArticle})
