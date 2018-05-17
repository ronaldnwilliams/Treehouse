# Generated by Django 2.0.5 on 2018-05-15 17:41

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('shoppers', '0002_auto_20180515_1705'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='shopper',
            name='stores',
        ),
        migrations.RemoveField(
            model_name='store',
            name='items',
        ),
        migrations.AddField(
            model_name='item',
            name='store',
            field=models.ManyToManyField(to='shoppers.Store'),
        ),
        migrations.AddField(
            model_name='store',
            name='shopper',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, to='shoppers.Shopper'),
            preserve_default=False,
        ),
    ]
