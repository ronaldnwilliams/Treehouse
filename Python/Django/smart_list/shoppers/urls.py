from django.urls import path, re_path

from . import views


urlpatterns = [
    path('', views.shopping_list, name='index'),
    path('<int:id>', views.shopping_list, name='store'),
    path('<int:store_id>/add', views.add_item, name='add'),
    path('<int:store_id>/toggle', views.toggle_item, name='toggle'),
    path('<int:store_id>/clear', views.clear_items, name='clear'),
    re_path(r'search/$', views.search_items, name='search'),
]
