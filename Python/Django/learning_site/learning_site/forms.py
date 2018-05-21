from django import forms
from django.core import validators

'''
You could use a custom function like this instead of django
built in validators

def must_be_empty(value):
    if value:
        raise forms.ValidationError('Is not empty')
'''


class SuggestionForm(forms.Form):
    name = forms.CharField()
    email = forms.EmailField()
    verify_email = forms.EmailField(label="Please verify your email address")
    suggestion = forms.CharField(widget=forms.Textarea)
    honeypot = forms.CharField(
        required=False,
        widget=forms.HiddenInput,
        label="Leave empty",
        validators=[validators.MaxLengthValidator(0)]
    )

    def clean(self):
        cleaned_data = super().clean()
        if cleaned_data.get('email') != cleaned_data.get('verify_email'):
            raise forms.ValidationError("Emails do not match")
