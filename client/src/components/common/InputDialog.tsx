"use client";

import { useEffect, useRef, useState } from "react";

export default function InputDialog({
  initialValue,
  isOpen,
  onClose,
  onSave,
  closeTitle,
}: {
  isOpen: boolean;
  onClose: () => void;
  initialValue?: string;
  closeTitle?: string;
  onSave: (notes: string) => void;
}) {
  const [notes, setNotes] = useState(initialValue ?? "");
  const textareaRef = useRef<HTMLTextAreaElement | null>(null);

  console.log(initialValue);

  useEffect(() => {
    setNotes(initialValue ?? "");
    if (isOpen) textareaRef.current?.focus();
  }, [isOpen, initialValue]);

  const handleOk = () => {
    onSave(notes);
    setNotes("");
    onClose();
  };

  return (
    <div>
      {isOpen && (
        <div
          role="dialog"
          aria-modal="true"
          className="fixed inset-0 z-50 flex items-center justify-center"
        >
          <div
            className="fixed inset-0 bg-black/50"
            onClick={onClose}
            aria-hidden
          />

          <div className="relative w-full max-w-lg mx-4 bg-black rounded-lg shadow-lg ring-1 ring-black/5">
            <div className="p-4">
              <h3 className="text-lg font-semibold">Notes</h3>

              <label className="mt-3 block text-sm text-gray-600">
                Write your notes below
              </label>
              <textarea
                ref={textareaRef}
                value={notes}
                onChange={(e) => setNotes(e.target.value)}
                className="mt-2 w-full min-h-[120px] resize-y rounded-md border border-gray-200 p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
                placeholder="Type your notes..."
              />

              <div className="mt-4 flex justify-end gap-2">
                <button
                  onClick={() => {
                    setNotes("");
                    onClose();
                  }}
                  className="px-4 py-2 rounded-md bg-blue-600 hover:bg-blue-700 focus:outline-none"
                >
                  {closeTitle ?? "Cancel"}
                </button>

                <button
                  onClick={handleOk}
                  className="px-4 py-2 rounded-md bg-blue-600 text-white hover:bg-blue-700 focus:outline-none"
                >
                  OK
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
