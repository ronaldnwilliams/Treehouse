from django.urls import path

from . import views


urlpatterns = [
    path('', views.teacher_list, name='list'),
]
