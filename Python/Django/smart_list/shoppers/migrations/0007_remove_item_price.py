# Generated by Django 2.0.5 on 2018-05-15 20:34

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('shoppers', '0006_store_last_modified'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='item',
            name='price',
        ),
    ]
