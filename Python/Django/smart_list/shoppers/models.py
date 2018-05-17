from django.db import models


class Store(models.Model):
    name = models.CharField(max_length=64)
    last_modified = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.name

class ItemManager(models.Manager):
    def create_item(self, name, store):
        item = self.create(name=name, store=store)
        return item

class Item(models.Model):
    name = models.CharField(max_length=128)
    checked = models.BooleanField(default=False)
    store = models.ForeignKey(Store, null=True, on_delete=models.SET_NULL)

    objects = ItemManager()

    def __str__(self):
        return '%s' % (self.name)
