// dll.c
#include "dll.h"
#include <assert.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

DLL init() {
  DLL dll = {NULL, NULL, 0};
  return dll;
}

bool empty(const DLL *dll) { return dll->size == 0; }

void prepend(DLL *dll, const char *data) {
  DLNode *node = malloc(sizeof(DLNode));
  if (!node)
    exit(-1);

  node->data = strdup(data);
  node->prev = NULL;
  node->next = dll->first;

  if (dll->first)
    dll->first->prev = node;
  dll->first = node;

  if (!dll->last) // first node added
    dll->last = node;

  dll->size++;
}

void append(DLL *dll, const char *data) {
  DLNode *node = malloc(sizeof(DLNode));
  if (!node)
    exit(-1);

  node->data = strdup(data);
  node->next = NULL;
  node->prev = dll->last;

  if (dll->last)
    dll->last->next = node;
  dll->last = node;

  if (!dll->first) // first node added
    dll->first = node;

  dll->size++;
}

void insert_at(DLL *dll, const char *data, size_t index) {
  if (index > dll->size)
    return;

  if (index == 0) {
    prepend(dll, data);
    return;
  }

  if (index == dll->size) {
    append(dll, data);
    return;
  }

  DLNode *current = dll->first;
  for (size_t i = 0; i < index - 1; i++)
    current = current->next;

  DLNode *node = malloc(sizeof(DLNode));
  if (!node)
    exit(-1);

  node->data = strdup(data);
  node->next = current->next;
  node->prev = current;

  current->next->prev = node;
  current->next = node;

  dll->size++;
}

void remove_at(DLL *dll, size_t index) {
  if (index >= dll->size)
    return;

  DLNode *current = dll->first;
  for (size_t i = 0; i < index; i++)
    current = current->next;

  if (current->prev)
    current->prev->next = current->next;
  else
    dll->first = current->next;

  if (current->next)
    current->next->prev = current->prev;
  else
    dll->last = current->prev;

  free(current->data);
  free(current);

  dll->size--;
}

void destroy(DLL *dll) {
  DLNode *current = dll->first;
  while (current) {
    DLNode *next = current->next;
    free(current->data);
    free(current);
    current = next;
  }
  dll->first = dll->last = NULL;
  dll->size = 0;
}

int main() {
  DLL list = init();
  assert(empty(&list));

  prepend(&list, "hallo!");
  prepend(&list, "world");
  append(&list, "do");
  insert_at(&list, "middle", 2);

  printf("List contents (%zu items):\n", list.size);
  for (DLNode *node = list.first; node != NULL; node = node->next) {
    printf("%s\n", node->data);
  }

  // Test removal
  remove_at(&list, 1); // remove "hallo!"
  printf("\nAfter removal:\n");
  for (DLNode *node = list.first; node != NULL; node = node->next) {
    printf("%s\n", node->data);
  }

  destroy(&list);
}
