from django.db import models

# Create your models here.
class Article(models.Model):
    created_at = models.DateTimeField(auto_now_add=True)
    title = models.CharField(max_length=255)
    subject = models.CharField(max_length=255)
    description = models.TextField()
    image_loc = models.CharField(max_length=255, blank=True)

    class Meta:
        ordering = ['-created_at',]

    def __str__(self):
        return self.title

class Comment(models.Model):
    created_at = models.DateTimeField(auto_now_add=True)
    comment = models.TextField()
    article = models.ForeignKey(Article, on_delete=models.CASCADE)

    class Meta:
        ordering = ['-created_at',]

    def __str__(self):
        return self.comment
