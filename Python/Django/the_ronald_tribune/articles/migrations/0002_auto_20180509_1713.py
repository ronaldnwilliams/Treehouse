# Generated by Django 2.0.5 on 2018-05-09 22:13

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('articles', '0001_initial'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='article',
            options={'ordering': ['-created_at']},
        ),
        migrations.AlterModelOptions(
            name='comment',
            options={'ordering': ['-created_at']},
        ),
        migrations.AddField(
            model_name='article',
            name='image_loc',
            field=models.CharField(blank=True, max_length=255),
        ),
    ]
