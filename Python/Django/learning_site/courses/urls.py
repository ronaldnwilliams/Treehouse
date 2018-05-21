from django.urls import path

from . import views


urlpatterns = [
    path('', views.course_list, name='list'),
    path('<int:pk>/', views.course_detail, name='detail'),
    path('<int:step_pk>/<int:course_pk>/', views.step_detail, name='step'),
]
