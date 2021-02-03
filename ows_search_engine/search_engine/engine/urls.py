from django.urls import path
from engine import views

urlpatterns = [
    path('search/<str:criteria>/<str:query>/',views.search,name='search'),
]
