# Generated by Django 2.0.5 on 2018-05-15 19:56

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('shoppers', '0005_auto_20180515_1746'),
    ]

    operations = [
        migrations.AddField(
            model_name='store',
            name='last_modified',
            field=models.DateTimeField(auto_now=True),
        ),
    ]
