from django.http import HttpResponseRedirect
from django.shortcuts import render
from django.urls import reverse

from .models import *


def shopping_list(request, id=1):
    context = {
        'selected_store': Store.objects.get(pk=id),
        'stores': Store.objects.all(),
        'non_items': Item.objects.exclude(store_id=id),
    }
    return render(request, 'shoppers/shopping_list.html', context)

def add_item(request, store_id):
    item_name = request.POST['name']

    store = Store.objects.get(pk=store_id)
    item, created = Item.objects.get_or_create(
        name=item_name,
        defaults={'store': store},
    )
    if item.store == None or item.store != store:
        item.store = store
    item.save()
    return HttpResponseRedirect(reverse('store', args=(store_id,)))

def toggle_item(request, store_id):
    item_id = int(request.POST['id'])

    store = Store.objects.get(pk=store_id)
    item = Item.objects.get(pk=item_id)
    item.checked = not item.checked
    item.save()
    return HttpResponseRedirect(reverse('store', args=(store_id,)))

def clear_items(request, store_id):
    selected_store = Store.objects.get(pk=store_id)
    items = Item.objects.filter(store=selected_store)
    for item in items:
        if item.checked:
            item.checked = not item.checked
            item.store = None
            item.save()
    return HttpResponseRedirect(reverse('store', args=(store_id,)))

def search_items(request):
    pass
